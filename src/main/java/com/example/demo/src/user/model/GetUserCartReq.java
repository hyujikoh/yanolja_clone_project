package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserCartReq {
    private int Idx;
    private String entrance;
    private String checktime;
    private String imageUrl;
    private String hotelName;
    private String hotelLocationDesc;
    private String roomType;
    private String roomName;
    private String allowedPeople;
    private String maxPeople;
    private String couponava;
    private String couponprices;
    private String reserveprice;
    private int stayprice_discount;

}
