package com.virtualoctopus.context;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusComponent;
import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusController;
import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusService;
import lombok.Data;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
        var reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(ALL)
                .setScanners(Scanners.TypesAnnotated));

        Set<Class<?>> controllers =
                reflections.getTypesAnnotatedWith(VirtualOctopusController.class);
        Set<Class<?>> components =
                reflections.getTypesAnnotatedWith(VirtualOctopusComponent.class);
        Set<Class<?>> services =
                reflections.getTypesAnnotatedWith(VirtualOctopusService.class);

        this.controllers = controllers.stream().map(clazz -> {
            try {
                return (Object) clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();


        this.components = components.stream().map(clazz -> {
            try {
                return (Object) clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();


        this.services = services.stream().map(clazz -> {
            try {
                return (Object) clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
}
