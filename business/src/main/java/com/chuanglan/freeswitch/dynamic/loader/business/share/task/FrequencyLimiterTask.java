package com.chuanglan.freeswitch.dynamic.loader.business.share.task;

import com.chuanglan.freeswitch.dynamic.loader.business.share.utils.FrequencyLimiter;

import java.util.TimerTask;

public class FrequencyLimiterTask extends TimerTask {

    private FrequencyLimiter limiter;

    public FrequencyLimiterTask(FrequencyLimiter limiter) {
        this.limiter = limiter;
    }

    @Override
    public void run() {
        if (!limiter.getIfPermit())
            limiter.setIfPermit(true);
        this.cancel();
    }
}
