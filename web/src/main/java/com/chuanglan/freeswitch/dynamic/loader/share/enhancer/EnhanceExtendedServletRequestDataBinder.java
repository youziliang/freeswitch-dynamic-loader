package com.chuanglan.freeswitch.dynamic.loader.share.enhancer;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.Enumeration;

/**
 * @Description 自定义参数绑定
 * @Author Youziliang
 * @Date 2018/12/28
 */
public class EnhanceExtendedServletRequestDataBinder extends ExtendedServletRequestDataBinder {

    EnhanceExtendedServletRequestDataBinder(Object target, String objectName) {
        super(target, objectName);
    }

    @Override
    public void bind(ServletRequest request) {
        MutablePropertyValues mpvs = new ServletRequestParameterPropertyValues(request);
        dataBind(request, mpvs);
        MultipartRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartRequest.class);
        if (multipartRequest != null) {
            bindMultipart(multipartRequest.getMultiFileMap(), mpvs);
        }
        addBindValues(mpvs, request);
        doBind(mpvs);
    }

    private void dataBind(ServletRequest request, MutablePropertyValues mpvs) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String userDefinition = attributeNames.nextElement();
            if (null != request.getAttribute(userDefinition))
                mpvs.addPropertyValue(userDefinition, request.getAttribute(userDefinition));
        }
    }


}