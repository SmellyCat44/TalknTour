package com.tracysong.speechdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracysong.speechdemo.entity.Users;
import com.tracysong.speechdemo.entity.Wish;
import org.springframework.stereotype.Component;

@Component
public interface WishMapper extends BaseMapper<Wish> {
}
