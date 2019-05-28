package com.chuanglan.freeswitch.dynamic.loader.share.enhancer;

import com.alibaba.fastjson.JSON;
import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Description 自定义ServletInvocableHandlerMethod
 * @Author Youziliang
 * @Date 2018/12/28
 */
@Slf4j
public class EnhanceServletInvocableHandlerMethod extends ServletInvocableHandlerMethod {

    private HttpServletRequest request;

    private HttpServletResponse response;

    public EnhanceServletInvocableHandlerMethod(Object handler, Method method) {
        super(handler, method);
    }

    public EnhanceServletInvocableHandlerMethod(HandlerMethod handlerMethod) {
        super(handlerMethod);
    }

    @Override
    public Object invokeForRequest(NativeWebRequest request, ModelAndViewContainer mavContainer, Object... providedArgs) throws Exception {
        this.request = (HttpServletRequest) request.getNativeRequest();
        this.response = (HttpServletResponse) request.getNativeResponse();
        return super.invokeForRequest(request, mavContainer, providedArgs);
    }

    @Override
    protected Object doInvoke(Object... args) throws Exception {
        Boolean ifEnableLogging = true;
        Object requestId = request.getAttribute(SystemConstant.REQUEST_ID);
        try {
            for (Object obj : args) {
                if (obj instanceof MultipartFile)
                    ifEnableLogging = false;
            }
            if (ifEnableLogging) {
                log.info("请求ID: {} 请求地址: {} 请求参数: {}",
                        requestId, request.getRequestURI(), JSON.toJSONString(args));
            } else {
                log.info("请求ID: {} 请求地址: {} 请求参数: {}",
                        requestId, request.getRequestURI(), "MultipartFile");
            }

            Object returnObj = super.doInvoke(args);

            if (returnObj instanceof DeferredResult)
                log.info("请求ID: {} 请求地址: {} 响应参数: {}",
                        requestId, request.getRequestURI(), "Async Response ...");
            else
                log.info("请求ID: {} 请求地址: {} 响应参数: {}",
                        requestId, request.getRequestURI(), JSON.toJSONString(returnObj));

            return returnObj;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
