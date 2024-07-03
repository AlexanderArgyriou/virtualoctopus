package com.virtualoctopus.utils.beanloader;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusRepository;
import com.virtualoctopus.annotations.dbase.VirtualOctopusQuery;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.List;

public final class RepositoryBeanLoader
        implements BeanLoader {
    public static BeanLoader newLoader() {
        return new RepositoryBeanLoader();
    }

    @Override
    public List<Object> loadBeans() {
        var reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(ALL)
                .setScanners(Scanners.TypesAnnotated));

        var classes =
                reflections.getTypesAnnotatedWith(VirtualOctopusRepository.class);

        return classes.stream()
                .map(c -> {
                    try {
                        return Proxy.newProxyInstance(
                                c.getClassLoader(),
                                new Class<?>[]{c},
                                (proxy, method, args) -> {
                                    if (method.isAnnotationPresent(VirtualOctopusQuery.class)) {
                                        String query = method.getAnnotation(VirtualOctopusQuery.class).query();
                                        String url = "jdbc:postgresql://localhost:5432/brl_catalog_db";
                                        String user = "brl_catalog";
                                        String password = "demo";

                                        try (Connection conn = DriverManager.getConnection(url, user, password)) {
                                            if (conn != null) {
                                                System.out.println("Connected to the database!");

                                                // Create a statement
                                                try (Statement stmt = conn.createStatement()) {
                                                    // Execute the query
                                                    ResultSet rs = stmt.executeQuery(query);
                                                    return rs.getMetaData();
                                                }
                                            } else {
                                                throw new SQLException("Could not connect to the database!");
                                            }
                                        } catch (SQLException e) {
                                            System.out.println(e.getMessage());
                                            return null;
                                        }
                                    } else {
                                        return method.invoke(c, args);
                                    }
                                });
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
    }
}
