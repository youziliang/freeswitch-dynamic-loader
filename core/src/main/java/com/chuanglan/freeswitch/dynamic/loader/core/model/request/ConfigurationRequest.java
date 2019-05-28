package com.chuanglan.freeswitch.dynamic.loader.core.model.request;

import com.chuanglan.freeswitch.dynamic.loader.core.model.request.base.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ConfigurationRequest extends BaseRequest {

    private String hostname;

    private String section;

    private String tag_name;

    private String key_name;

    private String key_value;

}
