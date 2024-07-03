package com.virtualoctopus.context;

import com.virtualoctopus.utils.beanloader.ComponentBeanLoader;
import com.virtualoctopus.utils.beanloader.ControllerBeanLoader;
import com.virtualoctopus.utils.beanloader.ServiceBeanLoader;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public final class BeanBucket {
    public static final String ALL = "";

    private List<Object> beans = new ArrayList<>();
    private List<Object> controllers = new ArrayList<>();
    private List<Object> services = new ArrayList<>();
    private List<Object> components = new ArrayList<>();

    public void addBean(Object bean) {
        beans.add(bean);
    }

    public List<Object> getBeansOfType(Class<?> type) {
        return beans.stream()
                .filter(bean -> type.isAssignableFrom(bean.getClass()))
                .collect(Collectors.toList());
    }

    public void loadBeans() {
        this.controllers =
                ControllerBeanLoader.newLoader().loadBeans();
        this.components =
                ComponentBeanLoader.newLoader().loadBeans();
        this.services =
                ServiceBeanLoader.newLoader().loadBeans();

        beans.addAll(services);
        beans.addAll(controllers);
        beans.addAll(components);
    }
}
