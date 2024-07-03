package com.virtualoctopus;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusRepository;
import com.virtualoctopus.annotations.dbase.VirtualOctopusQuery;

@VirtualOctopusRepository
public interface TestRepo {
    String url = "jdbc:postgresql://localhost:5432/brl_catalog_db";
    String user = "brl_catalog";
    String password = "demo";

    @VirtualOctopusQuery(query = "select * from databasechangelog")
    Object getAll();
}
