package com.virtualoctopus;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Counter {
    static Counter counter = new Counter();
    AtomicInteger count = new AtomicInteger(0);

    public static Counter singCounter() {
        return counter;
    }
}
