package com.virtualoctopus.utils.pathmappers;

import com.virtualoctopus.annotations.resource.mappings.ReactopusGetMapping;
import com.virtualoctopus.utils.beanloader.ControllerBeanLoader;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;

public class PathHandlerEnhancer {
    private final ControllerBeanLoader controllerBeanLoader =
            new ControllerBeanLoader();

    public void loadServerPaths(final HttpServer server) {
        Set<Class<?>> controllers =
                controllerBeanLoader.loadControllers();

        for (Class<?> clazz : controllers) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(ReactopusGetMapping.class)) {
                    ReactopusGetMapping getMapping
                            = method.getAnnotation(ReactopusGetMapping.class);
                    server.createContext(getMapping.path(), exchange -> {
                        Object controller;
                        try {
                            controller =
                                    clazz.getDeclaredConstructor().newInstance();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        String response;
                        try {
                            response = method.invoke(controller).toString();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        exchange.sendResponseHeaders(200, response.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    });
                }
            }
        }
    }
}
