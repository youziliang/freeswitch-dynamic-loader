package com.chuanglan.freeswitch.dynamic.loader.core.utils;

import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemCharset;
import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemPunctuation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * @Description 网络请求工具类
 * @Author Youziliang
 * @Date 2019/1/16
 */
public class HttpUtil {

    public static final String GET = "Get";
    public static final String POST = "Post";
    public static final String HEAD = "Head";
    public static final String PUT = "Put";
    public static final String DELETE = "Delete";
    public static final String TRACE = "Trace";
    public static final String PATCH = "Patch";
    public static final String OPTIONS = "Options";

    public static final String CONTENT_TYPE = "Content-Type: ";

    public static final String URLENCODED_TYPE = "application/x-www-form-urlencoded";
    public static final String JSON_TYPE = "application/json";
    public static final String XML_TYPE = "text/xml";

    public static final String CONTENT_TYPE_URLENCODED = CONTENT_TYPE + URLENCODED_TYPE;
    public static final String CONTENT_TYPE_JSON = CONTENT_TYPE + JSON_TYPE;
    public static final String CONTENT_TYPE_XML = CONTENT_TYPE + XML_TYPE;

    @Autowired
    @Qualifier("poolingHttpClientConnectionManager")
    private static PoolingHttpClientConnectionManager manager;

    @Autowired
    @Qualifier("requestConfig")
    private static RequestConfig requestConfig;

    private static List<Header> commonHeaderList;

    static {
        commonHeaderList = new ArrayList<>();
        commonHeaderList.add(new BasicHeader(HttpHeaders.ACCEPT, JSON_TYPE));
        commonHeaderList.add(new BasicHeader(HttpHeaders.ACCEPT_CHARSET, SystemCharset.UTF8));
    }

    public static List<Header> getHeaders(String type) {
        List<Header> headerList = new ArrayList<>(commonHeaderList);
        headerList.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, type));
        return headerList;
    }

    public static String request(String url) {
        return request(url, GET, null, null, null, false);
    }

    public static String request(String url, String methodType, Map<String, Object> paramsMap, List<Header> headerList) {
        return request(url, methodType, paramsMap, headerList, null, false);
    }

    public static String request(String url, String methodType, Map<String, Object> paramsMap, List<Header> headerList, Boolean openSSL) {
        return request(url, methodType, paramsMap, headerList, null, openSSL);
    }

    public static String request(String url, String methodType, Map<String, Object> paramsMap, List<Header> headerList, String encoding, Boolean openSSL) {
        List<NameValuePair> nvps = new ArrayList<>();
        try {
            if (StringUtils.equals(methodType, GET) && StringUtils.contains(url, SystemPunctuation.QUESTION_MARK))
                paramsMap = null;
            else
                url = StringUtils.equals(methodType, GET) && null != paramsMap ?
                        url + SystemPunctuation.QUESTION_MARK + EntityUtils.toString(new UrlEncodedFormEntity(map2NVPair(nvps, paramsMap), encoding)) : url;
            encoding = StringUtils.isBlank(encoding) ? SystemCharset.UTF8 : encoding;
            HttpRequestBase httpRequest = getHttpRequest(url, methodType);
            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(httpRequest.getClass())) {
                httpRequest.setHeaders(getHeaders(headerList));
                String contentType = httpRequest.getHeaders(HttpHeaders.CONTENT_TYPE)[0].toString();
                if (StringUtils.equals(CONTENT_TYPE_URLENCODED, contentType))
                    ((HttpEntityEnclosingRequestBase) httpRequest).setEntity(new UrlEncodedFormEntity(map2NVPair(nvps, paramsMap), encoding));
                if (StringUtils.equals(CONTENT_TYPE_JSON, contentType))
                    ((HttpEntityEnclosingRequestBase) httpRequest).setEntity(new StringEntity(FormatUtil.map2JsonStr(paramsMap), encoding));
                if (StringUtils.equals(CONTENT_TYPE_XML, contentType))
                    ((HttpEntityEnclosingRequestBase) httpRequest).setEntity(new StringEntity(FormatUtil.map2Xml(paramsMap), encoding));
            }
            return execute(httpRequest, encoding, openSSL);
        } catch (Exception e) {
            throw new RuntimeException("Http Exception: " + e.getMessage());
        }
    }

    private static String execute(HttpRequestBase httpRequest, String encoding, Boolean openSSL)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        CloseableHttpClient httpClient = getHttpClient(openSSL);
        CloseableHttpResponse response = httpClient.execute(httpRequest);
        int code = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        if (200 == code && entity != null) {
            String body = EntityUtils.toString(entity, encoding);
            EntityUtils.consume(entity);
            return body;
        } else {
            throw new RuntimeException("Http Exception: " + code +
                    " Cause: " + EntityUtils.toString(Objects.requireNonNull(entity), encoding));
        }
    }

    private static CloseableHttpClient getHttpClient(Boolean openSSL)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        CloseableHttpClient httpClient;
        if (openSSL) {
            httpClient = HttpClients.custom()
                    .setSSLSocketFactory(createSSLConnSocketFactory())
                    .setConnectionManager(manager)
                    .setDefaultRequestConfig(requestConfig).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLConnectionSocketFactory sslsf = null;
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
        sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }

            @Override
            public void verify(String host, SSLSocket ssl) {
            }

            @Override
            public void verify(String host, X509Certificate cert) {
            }

            @Override
            public void verify(String host, String[] cns, String[] subjectAlts) {
            }
        });
        return sslsf;
    }

    private static void close(HttpResponse resp) throws IOException {
        if (null != resp && CloseableHttpResponse.class.isAssignableFrom(resp.getClass()))
            ((CloseableHttpResponse) resp).close();
    }

    private static Header[] getHeaders(List<Header> headerList) {
        return headerList.toArray(new Header[]{});
    }

    private static HttpRequestBase getHttpRequest(String url, String methodType)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        methodType = "org.apache.http.client.methods.Http" + methodType;
        HttpRequestBase httpRequest = (HttpRequestBase) Class.forName(methodType).newInstance();
        httpRequest.setURI(URI.create(url));
        return httpRequest;
    }

    private static List<NameValuePair> map2NVPair(List<NameValuePair> nvps, Map<String, Object> map) {
        if (null != map && 0 < map.size()) {
            for (Entry<String, Object> entry : map.entrySet())
                if (StringUtils.isNotBlank(entry.getKey()) && null != entry.getValue())
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        return nvps;
    }

}
