

package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
public class GetUserReview {
    private int Idx;
    private int reviewRate;
    private String reviewText;
    private int reserveidx;
    private int roomIdx;
    private String hotelnames;
    private String roomType;
    private String roomDesc;
    private String Type;
    private String reviewImageUrl;
}
