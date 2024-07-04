package com.virtualoctopus;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusDbConfiguration;

@VirtualOctopusDbConfiguration(
        url = "jdbc:postgresql://localhost:5432/brl_catalog_db",
        username = "brl_catalog",
        password = "demo")
public class DbConfig {
}
