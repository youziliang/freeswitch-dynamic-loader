package com.chuanglan.freeswitch.dynamic.loader.core.model.request.base;

import com.chuanglan.framework.core.base.AbstractRequest;
import lombok.Data;

@Data
public class BaseRequest extends AbstractRequest {

    /**
     * 公众号的唯一标识
     */
    private String appid;

    /**
     * 公众号的appsecret
     */
    private String secret;

    /**
     * access_token
     */
    private String access_token;
}
