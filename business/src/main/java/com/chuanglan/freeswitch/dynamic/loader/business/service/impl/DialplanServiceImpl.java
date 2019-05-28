package com.chuanglan.freeswitch.dynamic.loader.business.service.impl;

import com.chuanglan.freeswitch.dynamic.loader.business.service.DialplanService;
import com.chuanglan.freeswitch.dynamic.loader.core.constants.TemplateConstants;
import com.chuanglan.framework.core.base.BaseException;
import com.chuanglan.framework.core.enums.BizCode;
import com.chuanglan.framework.template.ActionContext;
import com.chuanglan.framework.template.Processor;
import com.chuanglan.framework.template.support.TemplateManager;
import com.chuanglan.freeswitch.dynamic.loader.core.model.request.DialplanRequest;
import com.chuanglan.freeswitch.dynamic.loader.core.model.response.DialplanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("freeswitch.DialplanService")
public class DialplanServiceImpl implements DialplanService {

    @Autowired
    private TemplateManager templateManager;

    @Override
    public DialplanResponse getDialplan(DialplanRequest request) {
        DialplanResponse response = new DialplanResponse();
        ActionContext context = ActionContext.getContext();
        context.setRequest(request);
        context.setResponse(response);
        try {
            Processor processor = templateManager.getProcessor(TemplateConstants.DialplanProcess.GET_DIALPLAN);
            processor.process();
            response.setCode(BizCode.Success);
            response.setMessage("查询拨号计划成功");
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            response.setCode(BizCode.Unknown);
            response.setMessage("未知异常");
        } finally {
            ActionContext.remove();
        }
        return response;
    }

}
