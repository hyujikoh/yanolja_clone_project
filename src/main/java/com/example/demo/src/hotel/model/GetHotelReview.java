

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

public class GetHotelReview {
    private int reviewRate;
    private String reviewText;
    private String Posteddate;
    private String roomName;
    private String userNAME;
    private String reviewImageUrl;
}
