package com.virtualoctopus.utils.beanloader;

import java.util.List;

public sealed interface BeanLoader
        permits ComponentBeanLoader,
        ControllerBeanLoader,
        ServiceBeanLoader {
    String ALL = "";

    default List<Object> loadBeans() {
        throw new UnsupportedOperationException();
    }
}
