package com.tracysong.speechdemo.entity;

import com.google.type.DateTime;
import lombok.Data;

import java.sql.Date;

@Data
public class Review {
    private Integer reviewId;
    private String userId;
    private Integer score;
    private String comment;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private Date reviewDate;
    private String placeId;
}
