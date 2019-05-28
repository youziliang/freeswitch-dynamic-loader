package com.chuanglan.freeswitch.dynamic.loader.core.model.vo;

import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemSignal;
import com.chuanglan.framework.core.base.AbstractResponse;
import com.chuanglan.framework.core.enums.BizCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class RespVO {
    private static final String SUCCESSED_CODE = BizCode.Success.code();
    private static final String SUCCESSED_MSG = BizCode.Success.desc();
    private static final String FAILED_CODE = BizCode.Unknown.code();
    private static final String FAILED_MSG = BizCode.Unknown.desc();

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String msg;

    @Getter
    private Map<String, Object> data = new HashMap<>();

    public RespVO() {
        this.code = SUCCESSED_CODE;
        this.msg = SUCCESSED_MSG;
    }

    public RespVO(Long status) {
        if (SystemSignal.SUCCESSED.equals(status)) {
            this.code = SUCCESSED_CODE;
            this.msg = SUCCESSED_MSG;
        } else if (SystemSignal.FAILED.equals(status) || SystemSignal.EXCEPTION.equals(status)) {
            this.code = FAILED_CODE;
            this.msg = FAILED_MSG;
        }
    }

    public RespVO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RespVO(AbstractResponse resp, Boolean... conditions) {
        Boolean flag = true;
        for (Boolean condition : conditions) {
            flag = flag && condition;
        }
        if (null != resp && flag) {
            this.code = resp.getCode().code();
            this.msg = resp.getMessage();
        } else {
            this.code = FAILED_CODE;
            this.msg = FAILED_MSG;
        }
    }

    public RespVO put(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public RespVO putAll(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    public RespVO remove(String key) {
        this.data.remove(key);
        return this;
    }
}
