package com.chuanglan.freeswitch.dynamic.loader.business.action;

import com.chuanglan.freeswitch.dynamic.loader.core.model.request.DialplanRequest;
import com.chuanglan.framework.core.base.AbstractRequest;
import com.chuanglan.framework.core.base.AbstractResponse;
import com.chuanglan.framework.template.support.AbstractAction;
import com.chuanglan.freeswitch.dynamic.loader.core.model.response.DialplanResponse;
import org.springframework.stereotype.Component;

@Component("freeswitch.GetDialplanAction")
public class GetDialplanAction extends AbstractAction {

    @Override
    protected boolean doPerform(AbstractRequest abstractRequest, AbstractResponse abstractResponse) {

        DialplanRequest request = (DialplanRequest) abstractRequest;
        DialplanResponse response = (DialplanResponse) abstractResponse;

        return true;
    }
}
