

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

public class GetHotelinfo {
    private int Idx;
    private String hotelName;
    private String hotelLocation;
    private String grade;
    private int hotelOpt;
    private int auth;
    private String imageUrl;
    private int reviewcount;
    private float reviewavg;
    private String couponUseType;
    private String couponprice;

}
