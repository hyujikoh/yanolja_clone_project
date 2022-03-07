package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.hotel.model.GetHotelReview;
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


    @Transactional
    public PostLoginRes logIn_email(PostLoginReq postLoginReq) throws BaseException{
        User user = userDao.getPwd(postLoginReq);
        String encryptPwd;
        try {
            System.out.println("이메일 로그인 2");
            encryptPwd=new SHA256().encrypt(postLoginReq.getUserPwd());
            System.out.println("이메일 로그인 3");
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(user.getUserPwd().equals(encryptPwd)){
            int userIdx = user.getIdx();
            String jwt = jwtService.createJwt(userIdx);
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

}
