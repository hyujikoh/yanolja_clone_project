package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewRes {
    private int userIdx;
    private String reviewtext;
    private int reviewRate;
}
