package com.chuanglan.freeswitch.dynamic.loader.core.model.dto;

import com.chuanglan.freeswitch.dynamic.loader.core.model.dto.base.BaseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessTokenDTO extends BaseDTO {

    /**
     * token
     */
    private String access_token;

    /**
     * 过期时间 单位: 秒
     */
    private Long expires_in;

    /**
     * 刷新的token
     */
    private String refresh_token;

    /**
     * 公众号用户标识
     */
    private String openid;

    /**
     * 作用范围
     */
    private String scope;

    /**
     * 到期时间 单位: 秒
     */
    private Long expires;
}
