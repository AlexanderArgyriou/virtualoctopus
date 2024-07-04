package com.virtualoctopus.utils.beanloader;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusComponent;
import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusConfiguration;
import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusDbConfiguration;

import java.util.ArrayList;
import java.util.List;

public final class ConfigurationBeanLoader
        implements BeanLoader {
    public static BeanLoader newLoader() {
        return new ConfigurationBeanLoader();
    }

    @Override
    public List<Object> loadBeans() {
        List<Object> configs = new ArrayList<>(BeanLoaderUtils.loadBeansOfType(VirtualOctopusConfiguration.class));
        configs.addAll(BeanLoaderUtils.loadBeansOfType(VirtualOctopusDbConfiguration.class));
        return configs;
    }
}
