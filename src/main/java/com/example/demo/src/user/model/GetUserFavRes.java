package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserFavRes {
    private String hotelName;
    private String grade;
    private int hotelOpt;
    private int auth;
    private String hotelLocation;
    private int reviewcnt;
    private Float reviewavg;
    private int hotelIdx;
    private String stayprice;
    private int staydiscount;
    private String stayentrance;
    private String dayuseprice;
    private int dayusediscount;
    private String dayuseentrance;
    private String couponava;
    private String couponprice;
    private String imageUrl;
    private int userIdx;
}
