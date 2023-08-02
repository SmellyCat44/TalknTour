package com.tracysong.speechdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracysong.speechdemo.entity.Admin;
import com.tracysong.speechdemo.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UsersMapper extends BaseMapper<Users> {
}
