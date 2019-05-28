package com.chuanglan.freeswitch.dynamic.loader.web;

import com.chuanglan.freeswitch.dynamic.loader.core.model.request.DirectoryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * @Description 动态配置用户（号码）
 * @Author Youziliang
 * @Date 2019/5/27
 */
@Slf4j
@RestController
@RequestMapping("directory")
public class DirectoryController {

    @Autowired
    private HttpServletRequest request;

    /**
     * 动态生成用户（号码）
     *
     * @param req
     * @return
     */
    @GetMapping(value = "get")
    public String getDialplan(DirectoryRequest req) {

        //TODO 动态生成用户（号码）

        /*Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = request.getParameter(parameterName);
            log.info("动态生成用户（号码）参数 {} : {}", parameterName, parameterValue);
        }*/

        return "<?xml version='1.0' encoding='UTF-8' standalone='no'?>\n" +
                "<document type='freeswitch/xml'>\n" +
                "   <section name='directory'>\n" +
                "       <domain name='internal-7000'>\n" +
                "           <params>\n" +
                "               <param name='dial-string' value='{^^:sip_invite_domain=${dialed_domain}:presence_id=${dialed_user}@${dialed_domain}}${sofia_contact(*/${dialed_user}@${dialed_domain})},${verto_contact(${dialed_user}@${dialed_domain})}'/>\n" +
                "               <param name='jsonrpc-allowed-methods' value='verto'/>" +
                "           </params>\n" +
                "           <groups>\n" +
                "               <group name='default'>\n" +
                "                   <users>\n" +
                "                       <user id='1000' cacheable='false'>\n" +
                "                           <params>\n" +
                "                               <param name='password' value='1234qwer'/>\n" +
                "                               <param name='vm-password' value='1000'/>\n" +
                "                           </params>\n" +
                "                           <variables>\n" +
                "                               <variable name='toll_allow' value='domestic,international,local'/>\n" +
                "                               <variable name='accountcode' value='1000'/>\n" +
                "                               <variable name='user_context' value='out-by-gateway'/>\n" +
                "                               <variable name='effective_caller_id_name' value='Extension 1000'/>\n" +
                "                               <variable name='effective_caller_id_number' value='1000'/>\n" +
                "                               <variable name='outbound_caller_id_name' value='1000'/>\n" +
                "                               <variable name='outbound_caller_id_number' value='1000'/>\n" +
                "                               <variable name='callgroup' value='techsupport'/>\n" +
                "                           </variables>\n" +
                "                       </user>\n" +
                "                       <user id='1001' cacheable='false'>\n" +
                "                           <params>\n" +
                "                               <param name='password' value='1234qwer'/>\n" +
                "                               <param name='vm-password' value='1001'/>\n" +
                "                           </params>\n" +
                "                           <variables>\n" +
                "                               <variable name='toll_allow' value='domestic,international,local'/>\n" +
                "                               <variable name='accountcode' value='1001'/>\n" +
                "                               <variable name='user_context' value='out-by-gateway'/>\n" +
                "                               <variable name='effective_caller_id_name' value='Extension 1001'/>\n" +
                "                               <variable name='effective_caller_id_number' value='1001'/>\n" +
                "                               <variable name='outbound_caller_id_name' value='1001'/>\n" +
                "                               <variable name='outbound_caller_id_number' value='1001'/>\n" +
                "                               <variable name='callgroup' value='techsupport'/>\n" +
                "                           </variables>\n" +
                "                       </user>\n" +
                "                       <user id='1002' cacheable='false'>\n" +
                "                           <params>\n" +
                "                               <param name='password' value='1234qwer'/>\n" +
                "                               <param name='vm-password' value='1002'/>\n" +
                "                           </params>\n" +
                "                           <variables>\n" +
                "                               <variable name='toll_allow' value='domestic,international,local'/>\n" +
                "                               <variable name='accountcode' value='1002'/>\n" +
                "                               <variable name='user_context' value='out-by-gateway'/>\n" +
                "                               <variable name='effective_caller_id_name' value='Extension 1002'/>\n" +
                "                               <variable name='effective_caller_id_number' value='1002'/>\n" +
                "                               <variable name='outbound_caller_id_name' value='1002'/>\n" +
                "                               <variable name='outbound_caller_id_number' value='1002'/>\n" +
                "                               <variable name='callgroup' value='techsupport'/>\n" +
                "                           </variables>\n" +
                "                       </user>\n" +
                "                   </users>\n" +
                "               </group>\n" +
                "           </groups>\n" +
                "       </domain>\n" +
                "   </section>\n" +
                "</document>";
    }

}
