package com.tracysong.speechdemo.controller;

import com.tracysong.speechdemo.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Api(tags = "redis")
@RequestMapping("/redis")
@RestController
public class KeyController {
    private final RedisService redisService;

    public KeyController(RedisService redisService) {
        this.redisService = redisService;
    }

    @ApiOperation(value = "get_key_test")
    @GetMapping("/keywords")
    public Mono<List<String>> getRedisValue() {
        return redisService.getSortedSet("keywords").collectList();
    }

    @ApiOperation(value = "test")
    @GetMapping("/test")
    public Object getValue() {
        return redisService.getValue("name");
    }

}
