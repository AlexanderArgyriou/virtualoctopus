package com.virtualoctopus.utils.beanloader;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusService;

import java.util.List;

public final class ServiceBeanLoader
        implements BeanLoader {
    public static BeanLoader newLoader() {
        return new ServiceBeanLoader();
    }

    @Override
    public List<Object> loadBeans() {
        return BeanLoaderUtils.loadBeansOfType(VirtualOctopusService.class);
    }
}
