package com.chuanglan.freeswitch.dynamic.loader.business.share.utils;

import org.springframework.cglib.beans.BeanCopier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description BeanCopy缓存工具类
 * @Author Youziliang
 * @Date 2018/12/29
 */
public class Copier<S, T> {

    private static volatile Map<String, BeanCopier> copiers = new ConcurrentHashMap<>();

    public synchronized T copy(S source, T target) {
        String key = source.getClass().getName() + target.getClass().getName();
        BeanCopier copier = copiers.get(key);
        if (null != copier) {
            copier.copy(source, target, null);
        } else {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            copier.copy(source, target, null);
            copiers.put(key, copier);
        }
        return target;
    }
}
