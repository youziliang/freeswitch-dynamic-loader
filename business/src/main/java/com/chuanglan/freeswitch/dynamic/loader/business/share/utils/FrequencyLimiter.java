package com.chuanglan.freeswitch.dynamic.loader.business.share.utils;

import com.chuanglan.freeswitch.dynamic.loader.business.share.task.FrequencyLimiterTask;

import java.util.Timer;

public class FrequencyLimiter {

    private volatile Boolean ifPermit = true;

    private Timer timer;

    private long limitTime;

    public Boolean getIfPermit() {
        return ifPermit;
    }

    public void setIfPermit(Boolean ifPermit) {
        this.ifPermit = ifPermit;
        timer.schedule(new FrequencyLimiterTask(this), limitTime);
    }

    public FrequencyLimiter(Timer timer, long limitTime) {
        this.timer = timer;
        this.limitTime = limitTime;
    }
}
