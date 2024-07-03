package com.virtualoctopus;

import com.virtualoctopus.context.OctopusAppContext;
import com.virtualoctopus.server.VirtualOctopusServer;

public class Main {
    public static void main(String[] args) {
        var octopusAppContext = OctopusAppContext.freshContext();
        var virtualOctopusServer =
                new VirtualOctopusServer(9090, "/", octopusAppContext.getBeanBucket());
        virtualOctopusServer.start();
    }
}