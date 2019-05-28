package com.chuanglan.freeswitch.dynamic.loader.web;

import com.chuanglan.freeswitch.dynamic.loader.core.model.request.ConfigurationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/**
 * @Description 动态配置启动项（Profile、Gateway等）
 * @Author Youziliang
 * @Date 2019/5/27
 */
@Slf4j
@RestController
@RequestMapping("configuration")
public class ConfigurationController {

    @Autowired
    private HttpServletRequest request;

    /**
     * @param req
     * @return
     */
    @GetMapping(value = "get")
    public String getDialplan(ConfigurationRequest req) {

        //TODO 动态生成配置文件
        //TODO 将Profile价值和Gateway加载分开执行

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = request.getParameter(parameterName);
            log.info("动态生成配置文件 参数 {} : {}", parameterName, parameterValue);
        }


        return "<?xml version='1.0' encoding='UTF-8' standalone='no'?>\n" +
                "<document type='freeswitch/xml'>\n" +
                "   <section name='configuration'>\n" +
                "       <configuration name='sofia.conf' description='sofia Endpoint'>\n" +
                "           <profiles>\n" +
                "               <profile name='internal-7000'>\n" +
                "                 <aliases>\n" +
                "                       <alias name='internal-7000'/>\n" +
                "                 </aliases>\n" +
                "                 <gateways>\n" +
                "                 </gateways>\n" +
                "                 <domains>\n" +
                "                   <domain name='all' alias='false' parse='false'/>\n" +
                "                 </domains>\n" +
                "                 <settings>\n" +
                "                   <param name='debug' value='0'/>\n" +
                "                   <param name='sip-trace' value='no'/>\n" +
                "                   <param name='sip-capture' value='no'/>\n" +
                "                   <param name='watchdog-enabled' value='no'/>\n" +
                "                   <param name='watchdog-step-timeout' value='30000'/>\n" +
                "                   <param name='watchdog-event-timeout' value='30000'/>\n" +
                "                   <param name='log-auth-failures' value='false'/>\n" +
                "                   <param name='forward-unsolicited-mwi-notify' value='false'/>\n" +
                "                   <param name='context' value='out-by-gateway'/>\n" +
                "                   <param name='rfc2833-pt' value='101'/>\n" +
                "                   <param name='sip-port' value='7000'/>\n" +
                "                   <param name='dialplan' value='XML'/>\n" +
                "                   <param name='dtmf-duration' value='2000'/>\n" +
                "                   <param name='inbound-codec-prefs' value='$${global_codec_prefs}'/>\n" +
                "                   <param name='outbound-codec-prefs' value='$${global_codec_prefs}'/>\n" +
                "                   <param name='rtp-timer-name' value='soft'/>\n" +
                "                   <param name='rtp-ip' value='$${local_ip_v4}'/>\n" +
                "                   <param name='sip-ip' value='$${local_ip_v4}'/>\n" +
                "                   <param name='hold-music' value='$${hold_music}'/>\n" +
                "                   <param name='apply-nat-acl' value='nat.auto'/>\n" +
                "                   <param name='apply-inbound-acl' value='domains'/>\n" +
                "                   <param name='local-network-acl' value='localnet.auto'/>\n" +
                "                   <param name='record-path' value='$${recordings_dir}'/>\n" +
                "                   <param name='record-template' value='${caller_id_number}.${target_domain}.${strftime(%Y-%m-%d-%H-%M-%S)}.wav'/>\n" +
                "                   <param name='manage-presence' value='true'/>\n" +
                "                   <param name='presence-hosts' value='$${domain},$${local_ip_v4}'/>\n" +
                "                   <param name='presence-privacy' value='$${presence_privacy}'/>\n" +
                "                   <param name='inbound-codec-negotiation' value='generous'/>\n" +
                "                   <param name='tls' value='$${internal_ssl_enable}'/>\n" +
                "                   <param name='tls-only' value='false'/>\n" +
                "                   <param name='tls-bind-params' value='transport=tls'/>\n" +
                "                   <param name='tls-sip-port' value='$${internal_tls_port}'/>\n" +
                "                   <param name='tls-passphrase' value=''/>\n" +
                "                   <param name='tls-verify-date' value='true'/>\n" +
                "                   <param name='tls-verify-policy' value='none'/>\n" +
                "                   <param name='tls-verify-depth' value='2'/>\n" +
                "                   <param name='tls-verify-in-subjects' value=''/>\n" +
                "                   <param name='tls-version' value='$${sip_tls_version}'/>\n" +
                "                   <param name='tls-ciphers' value='$${sip_tls_ciphers}'/>\n" +
                "                   <param name='odbc-dsn' value='freeswitch:root:youziliang'/>\n" +
                "                   <param name='inbound-late-negotiation' value='true'/>\n" +
                "                   <param name='inbound-zrtp-passthru' value='true'/>\n" +
                "                   <param name='accept-blind-reg' value='false'/>\n" +
                "                   <param name='nonce-ttl' value='60'/>\n" +
                "                   <param name='auth-calls' value='$${internal_auth_calls}'/>\n" +
                "                   <param name='inbound-reg-force-matching-username' value='true'/>\n" +
                "                   <param name='auth-all-packets' value='false'/>\n" +
                "                   <param name='ext-rtp-ip' value='106.14.226.64'/>\n" +
                "                   <param name='ext-sip-ip' value='106.14.226.64'/>\n" +
                "                   <param name='rtp-timeout-sec' value='300'/>\n" +
                "                   <param name='rtp-hold-timeout-sec' value='1800'/>\n" +
                "                   <param name='ws-binding'  value=':5066'/>\n" +
                "                   <param name='wss-binding' value=':7443'/>\n" +
                "                   <param name='challenge-realm' value='auto_from'/>\n" +
                "                 </settings>\n" +
                "               </profile>\n" +
                "               <profile name='external'>\n" +
                "                 <gateways>\n" +
                "                   <gateway name='gw1'>\n" +
                "                       <param name='realm' value='47.100.219.154:5080'/>\n" +
                "                       <param name='username' value='1019'/>\n" +
                "                       <param name='password' value='1234qwer'/>\n" +
                "                       <param name='register' value='true'/>\n" +
                "                       <param name='from-domain' value='253'/>\n" +
                "                   </gateway>\n" +
                "                 </gateways>\n" +
                "                 <aliases></aliases>\n" +
                "                 <domains>\n" +
                "                   <domain name='all' alias='false' parse='true'/>\n" +
                "                 </domains>\n" +
                "                 <settings>\n" +
                "                   <param name='debug' value='0'/>\n" +
                "                   <param name='sip-trace' value='no'/>\n" +
                "                   <param name='sip-capture' value='no'/>\n" +
                "                   <param name='rfc2833-pt' value='101'/>\n" +
                "                   <param name='sip-port' value='$${external_sip_port}'/>\n" +
                "                   <param name='dialplan' value='XML'/>\n" +
                "                   <param name='context' value='public'/>\n" +
                "                   <param name='dtmf-duration' value='2000'/>\n" +
                "                   <param name='inbound-codec-prefs' value='$${global_codec_prefs}'/>\n" +
                "                   <param name='outbound-codec-prefs' value='$${outbound_codec_prefs}'/>\n" +
                "                   <param name='hold-music' value='$${hold_music}'/>\n" +
                "                   <param name='rtp-timer-name' value='soft'/>\n" +
                "                   <param name='local-network-acl' value='localnet.auto'/>\n" +
                "                   <param name='manage-presence' value='false'/>\n" +
                "                   <param name='inbound-codec-negotiation' value='generous'/>\n" +
                "                   <param name='nonce-ttl' value='60'/>\n" +
                "                   <param name='auth-calls' value='false'/>\n" +
                "                   <param name='inbound-late-negotiation' value='true'/>\n" +
                "                   <param name='inbound-zrtp-passthru' value='true'/>\n" +
                "                   <param name='rtp-ip' value='$${local_ip_v4}'/>\n" +
                "                   <param name='sip-ip' value='$${local_ip_v4}'/>\n" +
                "                   <param name='ext-rtp-ip' value='auto-nat'/>\n" +
                "                   <param name='ext-sip-ip' value='auto-nat'/>\n" +
                "                   <param name='rtp-timeout-sec' value='300'/>\n" +
                "                   <param name='rtp-hold-timeout-sec' value='1800'/>\n" +
                "                   <param name='tls' value='$${external_ssl_enable}'/>\n" +
                "                   <param name='tls-only' value='false'/>\n" +
                "                   <param name='tls-bind-params' value='transport=tls'/>\n" +
                "                   <param name='tls-sip-port' value='$${external_tls_port}'/>\n" +
                "                   <param name='tls-passphrase' value=''/>\n" +
                "                   <param name='tls-verify-date' value='true'/>\n" +
                "                   <param name='tls-verify-policy' value='none'/>\n" +
                "                   <param name='tls-verify-depth' value='2'/>\n" +
                "                   <param name='tls-verify-in-subjects' value=''/>\n" +
                "                   <param name='tls-version' value='$${sip_tls_version}'/>\n" +
                "                 </settings>\n" +
                "               </profile>\n" +
                "           </profiles>\n" +
                "       </configuration>\n" +
                "   </section>\n" +
                "</document>";
    }

}
