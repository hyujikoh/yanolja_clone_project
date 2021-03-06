package com.example.demo.src.hotel;



import com.example.demo.config.BaseException;
import com.example.demo.src.hotel.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class HotelService {
//    final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private final HotelDao hotelDao;
//    private final HotelProvider hotelProvider;
//    private final JwtService jwtService;
//
//
//    @Autowired
//    public HotelService(HotelDao hotelDao, HotelProvider hotelProvider, JwtService jwtService) {
//        this.hotelDao = hotelDao;
//        this.hotelProvider = hotelProvider;
//        this.jwtService = jwtService;
//
//    }
//
//    //POST 호텔 생성 클래스
//    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
//        System.out.println("CCC3");
//        //중복
//        if(hotelProvider.checkEmail(postUserReq.getUserEmail()) ==1){
//            throw new BaseException(POST_USERS_EXISTS_EMAIL);
//        }
//
//        String pwd;
//        try{
//            //암호화
//            pwd = new SHA256().encrypt(postUserReq.getUserPwd());
//            postUserReq.setUserPwd(pwd);
//
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
//        }
//        try{
//            System.out.println("CCC4");
//            int Idx = hotelDao.createUser(postUserReq);
//            //jwt 발급.
//            String jwt = jwtService.createJwt(Idx);
//            System.out.println(jwt);
//
//            return new PostUserRes(jwt,Idx);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
//        try{
//            int result = hotelDao.modifyUserName(patchUserReq);
//            if(result == 0){
//                throw new BaseException(MODIFY_FAIL_USERNAME);
//            }
//        } catch(Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    /* 오현직이 추가한 핸드폰번호 번경하는 서비스 처리기기*/
//   public void modifyUserPhone(PatchUserReq_userPhone patchUserReq_userPhone) throws BaseException {
//        try{
//            System.out.println("phone1");
//            int result = hotelDao.modifyUserPhone(patchUserReq_userPhone);
//            if(result == 0){
//                throw new BaseException(MODIFY_FAIL_USERNAME);
//            }
//        } catch(Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    /* 오현직이 추가한 핸드폰번호 번경하는 서비스 처리기기*/
//    public void modifyUserNickname(PatchUserNicknameReq patchUserNicknameReq) throws BaseException {
//        try{
//            System.out.println("service 닉네임");
//            int result = hotelDao.modifyUserNickname(patchUserNicknameReq);
//            if(result == 0){
//                throw new BaseException(MODIFY_FAIL_USERNICKNAME);
//            }
//        } catch(Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//    /* 오현직이 추가한 이메일 번경하는 서비스 처리기기*/
//    public void modifyUserEmail(PatchUserEmailReq patchUserEmailReq) throws BaseException {
//        try{
//            System.out.println("service 이메일");
//            int result = hotelDao.modifyUserEmail(patchUserEmailReq);
//            if(result == 0){
//                throw new BaseException(MODIFY_FAIL_USEREMAIL);
//            }
//        } catch(Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//    /* 오현직이 추가한 회원상태 번경하는 서비스 처리기기*/
//    public void modifyUserStatus(PatchUserStatusReq patchUserStatusReq) throws BaseException {
//        try{
//            System.out.println("service 유저상태");
//            int result = hotelDao.modifyUserStatus(patchUserStatusReq);
//            if(result == 0){
//                throw new BaseException(MODIFY_FAIL_PASSWORDERROR);
//            }
//        } catch(Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
