package com.chuanglan.freeswitch.dynamic.loader.share.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

    public static UserAgent getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return UserAgent.parseUserAgentString(userAgent);
    }

    public static String getRemoteDomain(HttpServletRequest request) {
        return request.getRemoteHost();
    }

    public static String getRealIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    public static String getRemoteSystem(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();
        return operatingSystem.getName();
    }

}
