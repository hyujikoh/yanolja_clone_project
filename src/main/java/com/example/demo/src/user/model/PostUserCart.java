package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserCart {
    private int userIdx;
    private int roomIdx;
    private int hotelIdx;
    private int priceIdx;
    private String entranceTime;
    private String checkoutTime;

}
