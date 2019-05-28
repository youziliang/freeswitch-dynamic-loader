package com.chuanglan.freeswitch.dynamic.loader.share.enhancer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.util.List;

/**
 * @Description 自定义RequestMappingHandlerAdapter
 * @Author Youziliang
 * @Date 2018/12/28
 */
public class EnhanceRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter implements InitializingBean {

    @Autowired
    private HttpMessageConverters httpMessageConverters;


    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.getMessageConverters().addAll(httpMessageConverters.getConverters());
    }

    @Override
    protected ServletInvocableHandlerMethod createInvocableHandlerMethod(HandlerMethod handlerMethod) {
        super.createInvocableHandlerMethod(handlerMethod);
        return new EnhanceServletInvocableHandlerMethod(handlerMethod);
    }

    @Override
    protected InitBinderDataBinderFactory createDataBinderFactory(List<InvocableHandlerMethod> binderMethods) {
        return new EnhanceWebDataBinderFactory(binderMethods, getWebBindingInitializer());
    }


}