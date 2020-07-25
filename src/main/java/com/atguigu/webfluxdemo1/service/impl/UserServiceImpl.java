package com.atguigu.webfluxdemo1.service.impl;

import com.atguigu.webfluxdemo1.entity.User;
import com.atguigu.webfluxdemo1.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    // 创建map来存储数据
    private final Map<Integer, User> users = new HashMap<>();

    public UserServiceImpl(){
        this.users.put(1,new User("lucy", "男", 20));
        this.users.put(2,new User("Mary", "女", 30));
        this.users.put(3,new User("Jack", "nv", 90));
    }

    @Override
    public Mono<User> getUserById(int id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(users.values());
    }

    @Override
    public Mono<Void> saveUserInfo(Mono<User> userMono) {
        return userMono.doOnNext(person->{
            // 向map集合里面放值
            int id = users.size() + 1;
            users.put(id, person);
        }).thenEmpty(Mono.empty());
    }
}
