package com.virtualoctopus.context;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusComponent;
import lombok.Data;

@VirtualOctopusComponent
@Data
public final class OctopusAppContext {
    private final BeanBucket beanBucket = new BeanBucket();
}
