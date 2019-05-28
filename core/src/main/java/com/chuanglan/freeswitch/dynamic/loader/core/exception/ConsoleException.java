package com.chuanglan.freeswitch.dynamic.loader.core.exception;

import com.chuanglan.framework.core.base.BaseException;
import com.chuanglan.framework.core.enums.BizCode;

public class ConsoleException extends BaseException {

    public ConsoleException() {
        super();
    }

    public ConsoleException(BizCode code) {
        super(code);
    }

    public ConsoleException(BizCode code, String message) {
        super(code, message);
    }

    public ConsoleException(BizCode code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public ConsoleException(BizCode code, Throwable cause) {
        super(code, cause);
    }
}
