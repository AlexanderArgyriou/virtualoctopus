package com.virtualoctopus.utils.beanloader;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusComponent;

import java.util.List;

public final class ComponentBeanLoader
        implements BeanLoader {
    public static BeanLoader newLoader() {
        return new ComponentBeanLoader();
    }

    @Override
    public List<Object> loadBeans() {
        return BeanLoaderUtils.loadBeansOfType(VirtualOctopusComponent.class);
    }
}
