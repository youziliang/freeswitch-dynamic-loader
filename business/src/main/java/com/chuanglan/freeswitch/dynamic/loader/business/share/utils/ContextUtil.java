package com.chuanglan.freeswitch.dynamic.loader.business.share.utils;

import com.chuanglan.freeswitch.dynamic.loader.core.constants.SystemConstant;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class ContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    public static DefaultListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        ContextUtil.context = context;
        beanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
    }

    public static ApplicationContext getApplicationContext() {
        return ContextUtil.context;
    }

    public static void createBean(
            String className,
            String beanKey,
            Map<String, Object> propertyMap,
            Map<String, String> referenceMap,
            List<Object> propertyConstructList,
            List<String> referenceConstructList,
            String destroyMethod
    ) {
        if (beanFactory.containsBeanDefinition(className)) {
            return;
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(className);
        if (propertyMap != null && !propertyMap.isEmpty()) {
            Iterator<String> propertyKeys = propertyMap.keySet().iterator();
            while (propertyKeys.hasNext()) {
                String keyString = propertyKeys.next();
                builder.addPropertyValue(keyString, propertyMap.get(keyString));
            }
        }
        if (referenceMap != null && !referenceMap.isEmpty()) {
            Iterator<String> referenceKeys = referenceMap.keySet().iterator();
            while (referenceKeys.hasNext()) {
                String keyString = referenceKeys.next();
                builder.addPropertyReference(keyString, referenceMap.get(keyString));
            }
        }
        if (propertyConstructList != null && !propertyConstructList.isEmpty()) {
            for (int i = 0; i < propertyConstructList.size(); i++) {
                builder.addConstructorArgValue(propertyConstructList.get(i));
            }
        }
        if (referenceConstructList != null && !referenceConstructList.isEmpty()) {
            for (int i = 0; i < referenceConstructList.size(); i++) {
                builder.addConstructorArgReference(referenceConstructList.get(i));
            }
        }
        if (destroyMethod != null && !destroyMethod.isEmpty()) {
            builder.setDestroyMethodName(destroyMethod);
        }
        builder.getBeanDefinition().setAttribute(SystemConstant.ID, beanKey);
        beanFactory.registerBeanDefinition(beanKey, builder.getBeanDefinition());
    }

    public static void createBean(String beanName, Class<? extends Object> requiredType) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(requiredType);
        beanDefinition.getPropertyValues();
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    public static Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    /**
     * @Attention requiredType应为接口类型
     */
    public static <T> T getBean(Class<T> requiredType) {
        return beanFactory.getBean(requiredType);
    }

    /**
     * @Attention requiredType应为接口类型
     */
    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return beanFactory.getBean(beanName, requiredType);
    }
}

