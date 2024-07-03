package com.virtualoctopus.utils.beanloader;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusController;

import java.util.List;

public final class ControllerBeanLoader
        implements BeanLoader {
    public static BeanLoader newLoader() {
        return new ControllerBeanLoader();
    }

    @Override
    public List<Object> loadBeans() {
        return BeanLoaderUtils.loadBeansOfType(VirtualOctopusController.class);
    }
}
