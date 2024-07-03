package com.virtualoctopus.utils.beanloader;

import lombok.experimental.UtilityClass;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.util.List;

import static com.virtualoctopus.utils.beanloader.BeanLoader.ALL;

@UtilityClass
public class BeanLoaderUtils {
    public List<Object> loadBeansOfType(Class<? extends Annotation> clazz) {
        var reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(ALL)
                .setScanners(Scanners.TypesAnnotated));

        var classes =
                reflections.getTypesAnnotatedWith(clazz);

        return classes.stream()
                .map(c -> {
                    try {
                        return (Object)
                                c.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
    }
}
