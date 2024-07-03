package com.virtualoctopus.utils.beanloader;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusController;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class ControllerBeanLoader {
    private static final String ALL = "";

    public Set<Class<?>> loadControllers() {
        var reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(ALL)
                .setScanners(Scanners.TypesAnnotated));
        return reflections.getTypesAnnotatedWith(VirtualOctopusController.class);
    }
}
