package com.virtualoctopus.utils.beanloader;

import java.util.List;

public sealed interface BeanLoader
        permits ComponentBeanLoader,
        ControllerBeanLoader,
        ServiceBeanLoader,
        RepositoryBeanLoader,
        ConfigurationBeanLoader {
    String ALL = "";

    default List<Object> loadBeans(final List<Object> configurations) {
        throw new UnsupportedOperationException();
    }

    default List<Object> loadBeans() {
        throw new UnsupportedOperationException();
    }
}
