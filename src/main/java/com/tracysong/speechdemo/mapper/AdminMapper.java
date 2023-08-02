package com.tracysong.speechdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracysong.speechdemo.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from admin where admin_id =#{admin_id}")
    Admin findAdminbyId(@Param("admin_id") String adminId);
}
