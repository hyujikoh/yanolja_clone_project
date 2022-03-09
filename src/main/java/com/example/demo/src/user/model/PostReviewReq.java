package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewReq {
    private int reviewRate;
    private String reviewText;
    private int reserveIdx;
    private int userIdx;
    private int rev1;
    private int rev2;
    private int rev3;
    private int rev4;
}
