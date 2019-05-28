package com.chuanglan.freeswitch.dynamic.loader.share.interceptor;

import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemConstant;
import com.chuanglan.freeswitch.dynamic.loader.core.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Description 登录拦截器
 * @Author Youziliang
 * @Date 2019/1/19
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static List<String> passWithUsualInfo;

    static {
        passWithUsualInfo = new ArrayList<>();
        passWithUsualInfo.add("/central-console/dialplan/get");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        request.setAttribute(SystemConstant.REQUEST_ID, MD5Util.getNonceStr());

        //log.info("请求ID: {} 请求地址: {}", requestId, request.getRequestURI());
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) {
        //处理请求完成后视图渲染之前的处理操作
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //视图渲染之后的操作
    }

    private void setResponse(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    }

}
