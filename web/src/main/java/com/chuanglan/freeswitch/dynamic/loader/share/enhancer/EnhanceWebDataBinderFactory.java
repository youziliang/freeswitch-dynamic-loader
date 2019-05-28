package com.chuanglan.freeswitch.dynamic.loader.share.enhancer;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.List;

/**
 * @Description 自定义ServletRequestDataBinderFactory
 * @Author Youziliang
 * @Date 2018/12/28
 */
public class EnhanceWebDataBinderFactory extends ServletRequestDataBinderFactory {

    public EnhanceWebDataBinderFactory(List<InvocableHandlerMethod> binderMethods, WebBindingInitializer initializer) {
        super(binderMethods, initializer);
    }

    @Override
    protected ServletRequestDataBinder createBinderInstance(
            @Nullable Object target, @Nullable String objectName, NativeWebRequest request) {
        return new EnhanceExtendedServletRequestDataBinder(target, objectName);
    }
}