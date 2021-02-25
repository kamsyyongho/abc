package com.skt.doss.common.utils;

import org.springframework.context.ApplicationContext;

import com.skt.doss.common.context.ApplicationContextProvide;

public class BeanUtils {
    public static Object getBean(String beanName) {
        ApplicationContext applicationContext = ApplicationContextProvide.getApplicationContext();
        return null;
    }
}
