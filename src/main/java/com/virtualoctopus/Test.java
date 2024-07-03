package com.virtualoctopus;

import com.virtualoctopus.annotations.resource.mappings.ReactopusGetMapping;
import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusController;

@VirtualOctopusController
public class Test {
    @ReactopusGetMapping(path = "/test-get-mapping")
    public String test() throws InterruptedException {
//        Random random = new Random();
//        double randomSleepTime = 0.1 + random.nextDouble() * 9.9; // Generates a random value between 0.5 and 2.0
//        long sleepTimeMillis = (long) (randomSleepTime * 1000);   // Convert seconds to milliseconds
//        Thread.sleep(sleepTimeMillis);
        System.out.println(Thread.currentThread().toString() + " " + Counter.singCounter().getCount().incrementAndGet());
        return "test";
    }
}
