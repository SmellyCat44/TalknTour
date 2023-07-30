package com.tracysong.speechdemo.entity;

import lombok.Data;

import java.util.List;

@Data
public class RedirectResponse {
    private Integer redirect;
    private List<String> placesSearchResponse;
    private String transcript;
}

