package com.virtualoctopus;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusController;
import com.virtualoctopus.annotations.dinjetion.VirtualOctopusInject;
import com.virtualoctopus.annotations.resource.mappings.VirtualOctopusGetMapping;

@VirtualOctopusController
public class Test {
    @VirtualOctopusInject
    TestRepo testRepo;

    @VirtualOctopusGetMapping(path = "/x")
    public Object x() {
        return testRepo.getAll();
    }
}
