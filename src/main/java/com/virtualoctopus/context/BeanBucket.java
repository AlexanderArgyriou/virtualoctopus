package com.virtualoctopus.context;

import com.virtualoctopus.utils.beanloader.*;
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
    private List<Object> repositories = new ArrayList<>();
    private List<Object> configs = new ArrayList<>();


    public void addBean(Object bean) {
        beans.add(bean);
    }

    public List<Object> getBeansOfType(Class<?> type) {
        return beans.stream()
                .filter(bean -> type.isAssignableFrom(bean.getClass()))
                .collect(Collectors.toList());
    }

    public Object getBeanOfType(Class<?> type) {
        return beans.stream()
                .filter(bean -> type.isAssignableFrom(bean.getClass()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("missing bean of type "
                        + type.getName()));
    }

    public void loadBeans() {
        this.configs =
                ConfigurationBeanLoader.newLoader().loadBeans();
        this.controllers =
                ControllerBeanLoader.newLoader().loadBeans();
        this.components =
                ComponentBeanLoader.newLoader().loadBeans();
        this.services =
                ServiceBeanLoader.newLoader().loadBeans();
        this.repositories =
                RepositoryBeanLoader.newLoader().loadBeans(configs);

        beans.addAll(services);
        beans.addAll(controllers);
        beans.addAll(components);
        beans.addAll(repositories);
        beans.addAll(configs);
    }
}
