package com.virtualoctopus.utils.beanloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusDbConfiguration;
import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusRepository;
import com.virtualoctopus.annotations.dbase.VirtualOctopusQuery;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RepositoryBeanLoader
        implements BeanLoader {
    public static BeanLoader newLoader() {
        return new RepositoryBeanLoader();
    }

    @Override
    public List<Object> loadBeans(final List<Object> configurations) {
        var reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(ALL)
                .setScanners(Scanners.TypesAnnotated));

        Object dbConfig = configurations.stream()
                .filter(c -> Arrays.stream(c.getClass().getAnnotations())
                        .anyMatch(VirtualOctopusDbConfiguration.class::isInstance))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        Class<?> dbConfigClass = dbConfig.getClass();

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

                                        if (query.startsWith("INSERT")) {
                                            Object toSave = args[0];
                                            String[] valuesToReplace = extractValues(query);
                                            for (String s : valuesToReplace) {
                                                String prop = s.substring(s.lastIndexOf(".") + 1);
                                                Object value =
                                                        toSave.getClass().getMethod("get" +
                                                                prop.substring(0, 1).toUpperCase() + prop.substring(1)).invoke(toSave);
                                                query = query.replace(s, value != null ? value.toString() : "");
                                            }
                                        }

                                        var result = getResultList((String) dbConfigClass.getMethod("getDbUrl").invoke(dbConfig),
                                                (String) dbConfigClass.getMethod("getDbUser").invoke(dbConfig),
                                                (String) dbConfigClass.getMethod("getDbPassword").invoke(dbConfig), query);
                                        return convertListToJson(result);
                                    } else {
                                        return method.invoke(c, args);
                                    }
                                });
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
    }

    private List<Map<String, String>> getResultList(String url, String user, String password, String query) {
        List<Map<String, String>> resultList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query);

                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    // Process rows
                    while (rs.next()) {
                        // Create a map to store the row values with column names as keys
                        Map<String, String> rowMap = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = rsmd.getColumnName(i);
                            String columnValue = rs.getString(i);
                            rowMap.put(columnName, columnValue);
                        }

                        // Add the row map to the list
                        resultList.add(rowMap);
                    }
                }
            } else {
                System.out.println("Failed to make connection!");
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return resultList;
    }

    private String convertListToJson(List<Map<String, String>> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert the list to a JSON string
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String[] extractValues(String sql) {
        // Regular expression to match the values inside the VALUES clause
        String regex = "VALUES\\s*\\(([^)]+)\\)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);

        if (matcher.find()) {
            String values = matcher.group(1);
            String[] result = values.split(",");
            for (int i = 0; i < result.length; i++) {
                result[i] = result[i].trim().replaceAll("^'|'$", "");
            }
            return result;
        } else {
            throw new IllegalArgumentException("No values found in the SQL statement");
        }
    }
}
