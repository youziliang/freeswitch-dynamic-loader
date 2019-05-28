package com.chuanglan.freeswitch.dynamic.loader.business.service;

import com.chuanglan.freeswitch.dynamic.loader.core.model.request.DialplanRequest;
import com.chuanglan.freeswitch.dynamic.loader.core.model.response.DialplanResponse;

public interface DialplanService {

    DialplanResponse getDialplan(DialplanRequest request);
}
