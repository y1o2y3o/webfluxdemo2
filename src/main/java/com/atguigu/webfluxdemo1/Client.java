package com.atguigu.webfluxdemo1;

import com.atguigu.webfluxdemo1.entity.User;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Client {
    public static void main(String[] args) {
        WebClient webClient = WebClient.create("http://localhost:15553");
        String id = "1";
        User userResult = webClient.get()
                .uri("/users/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .block();
        System.out.println(userResult);

        Flux<User> results = webClient.get().uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);
        results.map(stu->stu).buffer().doOnNext(System.out::println).blockFirst();
    }
}
