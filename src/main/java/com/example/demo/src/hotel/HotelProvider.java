package com.example.demo.src.hotel;


import com.example.demo.config.BaseException;
import com.example.demo.src.hotel.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//    public int checkEmail(String userEmail) throws BaseException{
//        try{
//            return hotelDao.checkEmail(userEmail);
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
//        User user = hotelDao.getPwd(postLoginReq);
//        String encryptPwd;
//        try {
//            encryptPwd=new SHA256().encrypt(postLoginReq.getPassword());
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
//        }
//
//        if(user.getUserPwd().equals(encryptPwd)){
//            int userIdx = user.getIdx();
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostLoginRes(userIdx,jwt);
//        }
//        else{
//            throw new BaseException(FAILED_TO_LOGIN);
//        }
//
//    }

}
