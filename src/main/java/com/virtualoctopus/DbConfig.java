package com.virtualoctopus;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusDbConfiguration;
import lombok.Data;

@VirtualOctopusDbConfiguration
@Data
public class DbConfig {
    private final String dbUrl = System.getenv("VCTOPUS_DB_URL");
    private final String dbUser = System.getenv("VCTOPUS_DB_USER");
    private final String dbPassword = System.getenv("VCTOPUS_DB_PASSWORD");
}
