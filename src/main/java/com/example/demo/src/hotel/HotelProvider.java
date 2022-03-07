package com.example.demo.src.hotel;


import com.example.demo.config.BaseException;
import com.example.demo.src.hotel.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class HotelProvider {

    private final HotelDao hotelDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public HotelProvider(HotelDao hotelDao, JwtService jwtService) {
        this.hotelDao = hotelDao;
        this.jwtService = jwtService;
    }

    public List<GetHotelResConditoin> getHotels() throws BaseException{
        try{

            System.out.println("1123");
            List<GetHotelResConditoin> getHotelResConditoin = hotelDao.getHotels();

            return getHotelResConditoin;

        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetHotelResConditoin> getHotelByhotelName(String hotelName) throws BaseException{
        try{
            System.out.println("3");
            List<GetHotelResConditoin> getHotelResConditoin = hotelDao.HotelByhotelName(hotelName);
            System.out.println("4");
            return getHotelResConditoin;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 호텔 룸 정보
@Transactional
    public List getHotelrooms(int hotelIdx) throws BaseException {
        try {
            List getHotelinfo = hotelDao.HotelInfo(hotelIdx);
            List hotelImg = hotelDao.HotelImages(hotelIdx); //O
            List roominfo = hotelDao.HotelRoomInfo(hotelIdx);
            List resultDetailList = new ArrayList<>(Arrays.asList(hotelImg,getHotelinfo,roominfo));
            return resultDetailList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public GetHotelRes getHotel(int hotelIdx) throws BaseException {
        try {
            System.out.println("호텔1개 검색 2");
            GetHotelRes getHotelRes = hotelDao.getHotel(hotelIdx);
            System.out.println("호텔1개 검색 끝전");
            return getHotelRes;
        } catch (Exception exception) {
            System.out.println("데이터베이스 에러");
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetHotelResConditoinType> getHotelByhotelType(String hotelType) throws BaseException{
        try{

            System.out.println("3");
            List<GetHotelResConditoinType> getHotelResConditoin12s = hotelDao.HotelByhotelType(hotelType);
            System.out.println("4");
            return getHotelResConditoin12s;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }


    public List<GetHotelReview> getHotelReview(int hotelIdx) throws BaseException{

        try{

            System.out.println("3");
            List<GetHotelReview> getHotelReviews = hotelDao.getHotelReviews(hotelIdx);
            System.out.println("4");
            return getHotelReviews;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
