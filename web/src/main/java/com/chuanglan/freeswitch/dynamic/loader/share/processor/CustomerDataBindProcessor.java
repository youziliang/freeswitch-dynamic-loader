package com.chuanglan.freeswitch.dynamic.loader.share.processor;

import com.chuanglan.freeswitch.dynamic.loader.business.share.utils.JwtUtil;
import com.chuanglan.freeswitch.dynamic.loader.core.model.dto.base.LoginInfo;

import javax.servlet.http.HttpServletRequest;

public class CustomerDataBindProcessor {

    public static void assembleLoginInfo(HttpServletRequest request) {
        LoginInfo loginInfo = JwtUtil.request2LoginInfo(request);

        if (null != loginInfo) {
            //TODO
        }
    }

    public static String assembleClientInfo(HttpServletRequest request) {

        //TODO
        return request.getRequestURI();
    }
}
