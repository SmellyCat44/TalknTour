package com.tracysong.speechdemo.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Wish {
    private Integer wishId;
    private String userId;
    private String placeId;
    private Date addDate;
}
