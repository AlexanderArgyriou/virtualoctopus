package com.virtualoctopus.context;

public class OctopusAppContext {

    private static final OctopusAppContext octopusAppContext
            = new OctopusAppContext();

    private OctopusAppContext() {}

    public static OctopusAppContext getInstance() {
        return octopusAppContext;
    }
}
