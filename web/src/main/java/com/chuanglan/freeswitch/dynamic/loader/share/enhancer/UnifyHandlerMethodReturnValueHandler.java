package com.chuanglan.freeswitch.dynamic.loader.share.enhancer;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Description 自定义返回值控制器
 * @Author Youziliang
 * @Date 2019/4/3
 */
public class UnifyHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }

    @Override
    public void handleReturnValue(
            Object returnValue,
            @Nullable MethodParameter returnType,
            @Nullable ModelAndViewContainer modelAndViewContainer,
            @Nullable NativeWebRequest nativeWebRequest) {

    }
}
