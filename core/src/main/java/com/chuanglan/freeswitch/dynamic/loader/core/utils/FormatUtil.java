package com.chuanglan.freeswitch.dynamic.loader.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemSignal;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Description 格式轉換工具类
 */
public class FormatUtil {

    /**
     * @param obj
     * @Description Object转换成Map
     */
    public static Map<String, Object> obj2Map(Object obj) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (obj == null)
            return map;
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (StringUtils.equalsIgnoreCase(key, "class"))
                continue;
            Method method = property.getReadMethod();
            Object value = method != null ? method.invoke(obj) : null;
            if (null == value)
                continue;
            map.put(key, value);
        }
        return map;
    }

    /**
     * @param obj
     * @Description Object转换成Map（fastjson实现）
     */
    public static Map<String, Object> objToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null)
            return map;
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(obj));
        Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * @Description XML TO Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> xml2Map(String strXML) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Document document = DocumentHelper.parseText(strXML);
        Element root = document.getRootElement();
        List<Element> elements = root.elements();
        for (Element element : elements) {
            map.put(element.getName(), element.getTextTrim());
        }
        return map;
    }

    /**
     * @Description Map TO XML
     */
    public static String map2Xml(Map<String, Object> map) throws Exception {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("xml");
        if (null == map || SystemSignal.ZERO == map.size())
            return root.getTextTrim();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            root.addElement(key).addText(map.get(key).toString());
        }
        StringWriter writer = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(writer);
        xmlWriter.setEscapeText(false);
        xmlWriter.write(document);
        return writer.toString();
    }

    /**
     * @Description request TO Map
     */
    public static Map<String, Object> req2Map(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Enumeration<?> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = request.getParameter(key);
            if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * @Description json TO Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(String jsonStr) {
        return JSONObject.parseObject(jsonStr, HashMap.class);
    }

    /**
     * @Description Map TO Json
     */
    public static String map2Json(Map<String, Object> data) {
        if (data == null) {
            return "{}";
        } else {
            StringBuffer sb = new StringBuffer();
            int entryIndex = 0;
            sb.append("{");
            for (Iterator var = data.entrySet().iterator(); var.hasNext(); ++entryIndex) {
                Map.Entry entry = (Map.Entry) var.next();
                if (entryIndex != 0) {
                    sb.append(",");
                }
                sb.append(entry.getKey());
                sb.append(":");
                sb.append(entry.getValue());
            }
            sb.append("}");
            return sb.toString();
        }
    }

    /**
     * @Description Map TO JsonStr
     */
    public static String map2JsonStr(Map<String, Object> data) {
        return null == data ? "{}" : JSONObject.toJSONString(data);
    }

    /**
     * @Description base64 To File
     */
    public static File base64ToFile(String base64Str, String filePath, String fileName, Map<Integer, Map<String, Object>> transform) {
        File dir = new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        filePath = StringUtils.endsWith(filePath, "/") ? filePath : filePath + "/";
        File file = new File(filePath + fileName);

        try (FileOutputStream fos = new FileOutputStream(file, true); BufferedOutputStream bos = new BufferedOutputStream(fos);) {
            byte[] bytes = Base64.getDecoder().decode(base64Str);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @Description String match RegEx
     */
    public static String strMatchRegex(String str, String pattern) {
        if (str.length() != pattern.length())
            return null;
        char[] patternArray = pattern.toCharArray();
        char[] strArray = str.toCharArray();
        for (int i = 0; i < patternArray.length; i++) {
            if (Character.isUpperCase(patternArray[i])) {
                strArray[i] = Character.toUpperCase(strArray[i]);
            } else {
                strArray[i] = Character.toLowerCase(strArray[i]);
            }
        }
        String result = new String(strArray);
        return result;
    }

}
