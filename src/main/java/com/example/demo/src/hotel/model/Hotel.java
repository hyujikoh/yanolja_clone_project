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
@Entity
@Table(name = "Hotel")
public class Hotel {
    @Id
    @Column(name="Idx")
    private int hotelIdx;
    private String hotelName;
    private String hotelLocationDesc;
    private int hotelType;
    private String locationType;
    private String hotelDesc;
    private String status;

    public Hotel() {

    }
}
