package com.virtualoctopus.context;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusComponent;
import com.virtualoctopus.annotations.di.VirtualOctopusInject;
import com.virtualoctopus.injection.Injector;
import lombok.Data;
import lombok.ToString;

@Data
public final class OctopusAppContext {
    @ToString.Exclude
    private final BeanBucket beanBucket = new BeanBucket();
    private final Injector injector = new Injector();

    public static OctopusAppContext freshContext() {
        return new OctopusAppContext().createContext();
    }

    private OctopusAppContext createContext() {
        beanBucket.loadBeans();
        beanBucket.addBean(this);
        injector.performDependencyInjection(beanBucket);
        return this;
    }
}
