package com.virtualoctopus.server;

import com.virtualoctopus.utils.pathmappers.PathHandlerEnhancer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ReactopusServer {
    private final HttpServer server;
    private final PathHandlerEnhancer pathHandlerEnhancer
            = new PathHandlerEnhancer();

    @SneakyThrows
    public ReactopusServer(
            final int port,
            final String initPath) {
        this.server = HttpServer.create(
                new InetSocketAddress(port), 0
        );
        server.createContext(initPath, BasePathHandler.getNew());
        pathHandlerEnhancer.loadServerPaths(server);
    }

    public void start() {
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
    }

    static class BasePathHandler
            implements HttpHandler {
        public static HttpHandler getNew() {
            return new BasePathHandler();
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Hello, from reactopus server!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class VirtualThreadFactory
            implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return Thread.ofVirtual().start(r);
        }
    }
}
