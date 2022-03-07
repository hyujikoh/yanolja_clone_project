package com.example.demo.src.hotel;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.hotel.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.SEARCH_ERROR_HOTELTYPE;

@RestController
@RequestMapping("/app/hotels")
public class HotelController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final HotelProvider hotelProvider;
    @Autowired
    private final HotelService HotelService;
    @Autowired
    private final JwtService jwtService;
/*
*
1. 호텔 조회 그냥 호텔 조회
* */



    public HotelController(HotelProvider hotelProvider, HotelService hotelService, JwtService jwtService){
        this.hotelProvider = hotelProvider;
        this.HotelService = hotelService;
        this.jwtService = jwtService;
    }


    /** ^^^^^^내가 만든 코드 ^^^^^^^
     * 호텔조회 API
     * [GET] /hotels
     * 호텔 번호 출력 API
     * [GET] /hotel?hotleName=
     * @return BaseResponse<List<GetUserRes>> 이것도 수정
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetHotelResConditoin>> HotelByhotelName(@RequestParam(required = false) String hotelName) { //
        try{
            if(hotelName == null){
                System.out.println("1");
                List<GetHotelResConditoin> getHotelResConditoin = hotelProvider.getHotels();
                System.out.println("0");
            return new BaseResponse<>(getHotelResConditoin);
            }
            // Get Users

            List<GetHotelResConditoin> getHotelResConditoin = hotelProvider.getHotelByhotelName(hotelName); // 검색조회
            System.out.println("2");
            return new BaseResponse<>(getHotelResConditoin);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** ^^^^^^내가 만든 코드 ^^^^^^^
     * 호텔조회 API
     * [GET] /hotels
     * 호텔 번호 출력 API
     * [GET] /hotel?hotleName=
     * @return BaseResponse<List<GetUserRes>> 이것도 수정
     */
    //Query String
    @ResponseBody
    @GetMapping("/hotelType") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetHotelResConditoin12>> HotelByhotelType(@RequestParam(required = false) String hotelType) { //
        try{

            List<GetHotelResConditoin12> getHotelByhotelType = hotelProvider.getHotelByhotelType(hotelType); // 검색조회
            System.out.println("2");
            return new BaseResponse<>(getHotelByhotelType);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 호텔 1개 조회 API 내가 수정한 코드!!!!!!!!!
     * [GET] /app/hotels
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    //변수명은 무조건 동일하게!!!!
    @ResponseBody
    @GetMapping("/{hotelIdx}") // (GET) 127.0.0.1:9000/app/hotels/:hotelIdx
    public BaseResponse<GetHotelRes> getHotel(@PathVariable("hotelIdx") int hotelIdx ){
        // Get Users
        try{
            System.out.println("호텔1개 검색 1 ");
            GetHotelRes getHotelRes = hotelProvider.getHotel(hotelIdx);
            System.out.println("호텔1개 검색 끝");
            return new BaseResponse<>(getHotelRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }




    /**
     * 특정 호텔 방 정보
     * [GET] /app/hotels/{hotelIdx}/room
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    //변수명은 무조건 동일하게!!!!
    @ResponseBody
    @GetMapping("/{hotelIdx}/room") // (GET) 127.0.0.1:9000/app/hotels/:hotelIdx
    public BaseResponse<List<String>> getHotelrooms(@PathVariable("hotelIdx") int hotelIdx ){
        // Get Users
        try{
            System.out.println("호텔방개 검색 1 ");
            List<String> getHotelrooms = hotelProvider.getHotelrooms(hotelIdx);
            System.out.println("호텔방개 검색 끝");
            return new BaseResponse<>(getHotelrooms);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }



    /** ^^^^^^내가 만든 코드 ^^^^^^^
     * 호텔조회 API
     * [GET] /hotels
     * 호텔 번호 출력 API
     * [GET] /hotel?hotleName=
     * @return BaseResponse<List<GetUserRes>> 이것도 수정
     */
    //Query String
    @ResponseBody
    @GetMapping("/{hotelIdx}/reviews") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetHotelReview>> HotelByReviews(@PathVariable("hotelIdx") int hotelIdx ) { //
        try{

            List<GetHotelReview> getHotelReviews = hotelProvider.getHotelReview(hotelIdx); // 검색조회
            System.out.println("2");
            return new BaseResponse<>(getHotelReviews);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }



}
