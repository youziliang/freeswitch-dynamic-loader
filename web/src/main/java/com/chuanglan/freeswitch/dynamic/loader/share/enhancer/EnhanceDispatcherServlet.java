package com.chuanglan.freeswitch.dynamic.loader.share.enhancer;

import com.chuanglan.freeswitch.dynamic.loader.business.share.utils.FrequencyLimiter;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Description 自定义DispatcherServlet
 * @Author Youziliang
 * @Date 2019/4/3
 */
public class EnhanceDispatcherServlet extends DispatcherServlet {

    private static final Set<String> CONSTRAINT_URIS = new HashSet<>();

    static {
        CONSTRAINT_URIS.add("");//TODO 超频限制URI
    }

    private static final Map<String, FrequencyLimiter> REQUEST_URIS = new HashMap<>();

    private static final Timer timer = new Timer();

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String requestURI = request.getRequestURI();
        if (CONSTRAINT_URIS.contains(requestURI)) {
            FrequencyLimiter limiter = REQUEST_URIS.get(requestURI);

            if (null != limiter && limiter.getIfPermit())
                limiter.setIfPermit(false);
            else {
                //TODO response 写出超频调用提示
                return;
            }
        }
        super.doService(request, response);

    }

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(request, response);
    }

    @Override
    protected void initStrategies(ApplicationContext context) {

        super.initStrategies(context);

        Map<String, HandlerMapping> matchingBeans =
                BeanFactoryUtils.beansOfTypeIncludingAncestors(
                        context, HandlerMapping.class, true, false);

        for (HandlerMapping handlerMapping : matchingBeans.values()) {

            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping rmhm = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = rmhm.getHandlerMethods();

                for (RequestMappingInfo rmi : handlerMethods.keySet()) {
                    PatternsRequestCondition prc = rmi.getPatternsCondition();

                    for (String uri : prc.getPatterns())
                        if (CONSTRAINT_URIS.contains(uri)) {
                            REQUEST_URIS.put(uri, new FrequencyLimiter(timer, 60 * 1000));
                        }
                }
            }
        }
    }
}
