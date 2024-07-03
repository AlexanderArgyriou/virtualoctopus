package com.virtualoctopus.injection;

import com.virtualoctopus.annotations.di.VirtualOctopusInject;
import com.virtualoctopus.context.BeanBucket;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Injector {
    public void performDependencyInjection(final BeanBucket beanBucket) {
        List<Object> beans = beanBucket.getBeans();

        beans.forEach(bean -> {
            Field[] fields = bean.getClass().getDeclaredFields();
            Arrays.stream(fields).forEach(f -> {
                Annotation[] annotations = f.getAnnotations();
                Arrays.stream(annotations).forEach(a -> {
                    if (a instanceof VirtualOctopusInject) {
                        Object beanToInject = beanBucket.getBeanOfType(f.getType());
                        f.setAccessible(true);
                        try {
                            f.set(bean, beanToInject);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            });
        });
    }
}
