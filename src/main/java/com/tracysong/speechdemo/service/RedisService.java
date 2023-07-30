package com.tracysong.speechdemo.service;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
public class RedisService {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public RedisService(ReactiveRedisTemplate<String, String> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    public Mono<Boolean> setValue(String key, String value) {
        return reactiveRedisTemplate.opsForValue().set(key, value);
    }

    public Mono<String> getValue(String key) {
        return reactiveRedisTemplate.opsForValue().get(key);
    }

    public Flux<String> getSortedSet(String key) {
        return reactiveRedisTemplate.opsForZSet().reverseRange("keywords", Range.unbounded());

    }
}