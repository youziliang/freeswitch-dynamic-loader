package com.chuanglan.freeswitch.dynamic.loader.dal.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

public class PrefixedBeanNameGenerator implements BeanNameGenerator {

    private static String PREFIX_STRING = "selfcare";

    public static final String POINT_STRING = ".";

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String fullClassName = definition.getBeanClassName();
        int lastPointPosition = fullClassName.lastIndexOf(".");
        String shortClassName = lastPointPosition >= 0 ? fullClassName.substring(lastPointPosition + 1) : fullClassName;

        return PREFIX_STRING + POINT_STRING + shortClassName;
    }
}
