package com.chuanglan.freeswitch.dynamic.loader.business.share.utils;

import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemConstant;
import com.chuanglan.freeswitch.dynamic.loader.core.model.dto.base.LoginInfo;
import com.chuanglan.freeswitch.dynamic.loader.core.utils.DateUtil;
import com.chuanglan.freeswitch.dynamic.loader.core.utils.FormatUtil;
import com.chuanglan.freeswitch.dynamic.loader.core.utils.MD5Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @Description JWT工具类
 * @Author Youziliang
 * @Date 2019/1/28
 */
@Slf4j
public class JwtUtil {

    private static final String KEY = "u0WkcrXYcE1L822IMe0mrcEm3mNuf26fQWmKaKNLgJWQlDBvoTLV8IpfExGHjPsn";

    /**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法
     */
    public static String generate(long timing, LoginInfo loginInfo) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long current = System.currentTimeMillis();
        Map<String, Object> claims = FormatUtil.objToMap(loginInfo);
        String subject = SystemConstant.SYSTEM;
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setId(MD5Util.getNonceStr())
                .setIssuedAt(new Date(current))
                .setSubject(subject)
                .signWith(signatureAlgorithm, KEY)
                .setExpiration(new Date(current + timing));//timing 毫秒
        return builder.compact();
    }

    /**
     * 解析成登录信息
     */
    public static LoginInfo token2LoginInfo(String token) {
        LoginInfo loginInfo = null;
        Claims claims = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();
        if (null != claims)
            //loginInfo = LoginConvertor.claimsToLoginInfo(claims);
            loginInfo = new LoginInfo();
        return loginInfo;
    }

    /**
     * 解析成登录信息
     */
    public static LoginInfo request2LoginInfo(HttpServletRequest request) {
        String token = request.getHeader(SystemConstant.TOKEN);
        if (StringUtils.isBlank(token))
            return null;
        LoginInfo loginInfo = null;
        Claims claims = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody();
        if (null != claims)
            //loginInfo = LoginConvertor.claimsToLoginInfo(claims);
            loginInfo = new LoginInfo();
        return loginInfo;
    }

    /**
     * 校验是否快过期（5分钟）
     */
    public static Boolean expiring(HttpServletRequest request) {
        String token = request.getHeader(SystemConstant.TOKEN);
        if (StringUtils.isBlank(token))
            return false;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token)
                    .getBody();
            Date exp = claims.get("exp", Date.class);
            Long accountId = claims.get("accountId", Long.class);
            long timing = DateUtil.standardDateStrToTimestamp(exp.toString()) / 1000 - DateUtil.getCurrentTimestamp();
            log.info("账号 {} 的TOKEN 还有 {} 分钟过期", accountId, timing / 60);
            return timing < 60 * 5;
        } catch (Exception e) {//Token已过期，交由拦截器处理
            return false;
        }
    }
}
