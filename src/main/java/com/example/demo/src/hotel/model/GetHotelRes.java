package com.example.demo.src.hotel.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor

@Table(name = "Hotel")
public class GetHotelRes {
    @Id
    @Column(name="Idx")
    private int hotelIdx;
    private String hotelName;
    private String hotelLocationDesc;
    private String locationType;
    private String hotelDesc;
}
