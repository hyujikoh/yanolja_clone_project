package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public List<GetUserRes> getUsers() throws BaseException{
        try{
            List<GetUserRes> getUserRes = userDao.getUsers();

            return getUserRes;

        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserRes> getUsersByEmail(String email) throws BaseException{
        try{
            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
                    }


    public GetUserRes getUser(int userIdx) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEmail(String userEmail) throws BaseException{
        try{
            return userDao.checkEmail(userEmail);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkReserveIdx(int reserveIdx) throws BaseException{
        try{
            return userDao.checkReserveIdx(reserveIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkHoteIdx(int hotelIdx,int userIdx) throws BaseException{
        try{
            return userDao.checkHotelIdx(hotelIdx,userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public PostLoginRes logIn_email(PostLoginReq postLoginReq) throws BaseException{
        User user = userDao.getPwd_email(postLoginReq);
        String password;
        try {
            //암호화
            password= new SHA256().encrypt(postLoginReq.getUserPwd());
            System.out.println("pwd:"+password);
            postLoginReq.setUserPwd(password);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(user.getUserPwd().equals(password)){
            int userIdx = user.getIdx();
            String jwt = jwtService.createJwt(userIdx);
            System.out.println("jwt:" + jwt);
            return new PostLoginRes(userIdx,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }


    }

    @Transactional
    public List<GetUserReview> getUserReview(int userIdx) throws BaseException{

        try{

            System.out.println("3");
            List<GetUserReview> getUserReviews = userDao.getUserReviews(userIdx);
            System.out.println("4");
            return getUserReviews;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public PostLoginRes logIn_phone(PostLoginPhoneReq postLoginPhoneReq) throws BaseException{
        User user = userDao.getPwd_phone(postLoginPhoneReq);
        String password;
        try {
            //암호화
            password= new SHA256().encrypt(postLoginPhoneReq.getUserPwd());
            System.out.println("pwd:"+password);
            postLoginPhoneReq.setUserPwd(password);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(user.getUserPwd().equals(password)){
            int userIdx = user.getIdx();
            String jwt = jwtService.createJwt(userIdx);
            System.out.println("jwt:" + jwt);
            return new PostLoginRes(userIdx,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }


    }

    @Transactional
    public List<GetUserCartReq> getUserCarts(int userIdx) throws BaseException{

        try{

            System.out.println("3");
            List<GetUserCartReq> getUserCartReqs = userDao.getUserCarts(userIdx);
            System.out.println("4");
            return getUserCartReqs;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetUserFavRes> GetUserFav(int userIdx) throws BaseException{
        try{
//            User user = userDao.getPwd_email(postLoginReq);



            System.out.println("3");
            //jwt 발급.
//            String jwt = jwtService.createJwt(userIdx);

//            if(user.getUserPwd().equals(password)){
//                int userIdx = user.getIdx();
//                String jwt = jwtService.createJwt(userIdx);
//                System.out.println("jwt:" + jwt);
//                return new PostLoginRes(userIdx,jwt);
//if ()
//            System.out.println(jwt);


            List<GetUserFavRes> getUserFavRes = userDao.getUserFav(userIdx);
            System.out.println("4");
            return getUserFavRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
