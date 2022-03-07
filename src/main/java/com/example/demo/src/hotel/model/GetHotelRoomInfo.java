package com.example.demo.src.hotel.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor

public class GetHotelRoomInfo {
    private int Idx;
    private int roomIdx;
    private String img;
    private String roomType;
    private String roomName;
    private String stay_price;
    private int roomCount;
    private int allowedPeople;
    private int maxPeople;
    private int stay_discountprice;
    private String dayuse_price;
    private int dayuse_discountprice;
    private String stay_entrance;
    private String dayuse_entrance;

}
