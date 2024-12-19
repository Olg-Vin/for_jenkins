package com.vinio;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        // Создаём сервер на порту 8081
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        // Регистрируем обработчик для пути "/test"
        server.createContext("/test", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "Hello, World! __new";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        // Запускаем сервер
        server.setExecutor(null); // Используем стандартный executor
        System.out.println("Server started on http://localhost:8081");
        server.start();
    }
}
