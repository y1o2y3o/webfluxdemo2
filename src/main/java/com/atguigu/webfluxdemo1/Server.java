package com.atguigu.webfluxdemo1;

import com.atguigu.webfluxdemo1.handler.UserHandler;
import com.atguigu.webfluxdemo1.service.UserService;
import com.atguigu.webfluxdemo1.service.impl.UserServiceImpl;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.*;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

public class Server {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.createReactorSercer();
        System.out.println("enter to exit");
        System.in.read();
    }
    // 1. 创建路由
    public RouterFunction<ServerResponse> routingFunction(){
        UserService userService = new UserServiceImpl();
        UserHandler handler = new UserHandler(userService);

        return RouterFunctions.route(
                RequestPredicates.GET("/users/{id}").and(accept(APPLICATION_JSON)),handler::getUserById
        ).andRoute(RequestPredicates.GET("/users").and(accept(APPLICATION_JSON)),handler::getAllUsers);
    }

    // 3. 创建服务器完成适配
    public void createReactorSercer(){
        RouterFunction<ServerResponse> router = routingFunction();
        HttpHandler httpHandler = toHttpHandler(router);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);

        // 创建服务器
        HttpServer httpServer = HttpServer.create();
        httpServer.handle(adapter).bindNow();
    }
}
