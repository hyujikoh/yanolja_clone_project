package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor

public class PostUserFavRes {
    private int userIdx;
    private int hotelIdx;
}
