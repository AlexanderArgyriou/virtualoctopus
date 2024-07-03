package com.virtualoctopus;

import com.virtualoctopus.server.ReactopusServer;

public class Main {
    public static void main(String[] args) {
        var reactopusServer =
                new ReactopusServer(9090, "/");
        reactopusServer.start();
    }
}