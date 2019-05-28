package com.chuanglan.freeswitch.dynamic.loader.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

public class PropertyUtil extends PropertyPlaceholderConfigurer {

    private static Properties props;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory factory, Properties props) throws BeansException {
        super.processProperties(factory, props);
        PropertyUtil.props = props;
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static Object setProperty(String key, String value) {
        return props.setProperty(key, value);
    }
}
