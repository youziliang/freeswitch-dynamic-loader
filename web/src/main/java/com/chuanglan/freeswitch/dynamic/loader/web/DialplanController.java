package com.chuanglan.freeswitch.dynamic.loader.web;

import com.chuanglan.freeswitch.dynamic.loader.core.model.request.DialplanRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * @Description 动态配置拨号计划
 * @Author Youziliang
 * @Date 2019/5/27
 */
@Slf4j
@RestController
@RequestMapping("dialplan")
public class DialplanController {

    @Autowired
    private HttpServletRequest request;

    /**
     * 动态生成拨号计划
     *
     * @param req
     * @return
     */
    @GetMapping(value = "get")
    public String getDialplan(DialplanRequest req) {

        //TODO 动态生成拨号计划

        /*Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = request.getParameter(parameterName);
            log.info("动态生成拨号计划参数 {} : {}", parameterName, parameterValue);
        }*/

        return "<?xml version='1.0' encoding='UTF-8' standalone='no'?>\n" +
                "<document type='freeswitch/xml'>\n" +
                "   <section name='dialplan' description='Dial Plan For FreeSwitch'>\n" +
                "       <context name='out-by-gateway'>\n" +
                "           <extension name='out call'>\n" +
                "               <condition field='destination_number' expression='^0(\\d+)$'>\n" +
                "                   <action application='bridge' data='sofia/gateway/gw1/$1'/>\n" +
                "               </condition>\n" +
                "           </extension>\n" +
                "       </context>\n" +
                "   </section>\n" +
                "</document>";
    }

}
