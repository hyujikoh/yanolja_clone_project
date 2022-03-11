package com.example.demo.src.user;



import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.SHA;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        System.out.println("CCC3");
        //중복
        if(userProvider.checkEmail(postUserReq.getUserEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        try{
            //암호화
            //pwd = new SHA256(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getUserPwd());
            String encryptPwd=new SHA256().encrypt(postUserReq.getUserPwd());
            postUserReq.setUserPwd(encryptPwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            System.out.println("CCC4");
            int Idx = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(Idx);
            System.out.println(jwt);

            return new PostUserRes(jwt,Idx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /* 오현직이 추가한 핸드폰번호 번경하는 서비스 처리기기*/
    public void modifyUserPhone(PatchUserReq_userPhone patchUserReq_userPhone) throws BaseException {
        try{
            System.out.println("phone1");
            int result = userDao.modifyUserPhone(patchUserReq_userPhone);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /* 오현직이 추가한 닉네임 번경하는 서비스 처리기기*/
    public void modifyUserNickname(PatchUserNicknameReq patchUserNicknameReq) throws BaseException {
        try{
            System.out.println("service 닉네임");
            int result = userDao.modifyUserNickname(patchUserNicknameReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNICKNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void modifyUserFavStatus(PatchUserFav patchUserFav) throws BaseException{
        try{
            System.out.println("service 닉네임");
            int result = userDao.modifyUserFavs(patchUserFav);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNICKNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }





    /* 오현직이 추가한 이메일 번경하는 서비스 처리기기*/
    public void modifyUserEmail(PatchUserEmailReq patchUserEmailReq) throws BaseException {
        try{
            System.out.println("service 이메일");
            int result = userDao.modifyUserEmail(patchUserEmailReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USEREMAIL);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 리뷰 텍스트 변경하기
    public void modifyReviewText(PatchUserReviewReq patchUserReviewReq) throws BaseException {
        try{
            System.out.println("service 리뷰변경");
            int result = userDao.modifyReviewText(patchUserReviewReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USEREMAIL);
            }


        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /* 오현직이 추가한 회원상태 번경하는 서비스 처리기기 성공!*/
    public void modifyUserStatus(PatchUserStatusReq patchUserStatusReq) throws BaseException {
        String password;
        PatchUserStatusReq patchUserStatusReq1 = userDao.getPwd_idx(patchUserStatusReq);
        try {
            //암호화

            password= new SHA256().encrypt(patchUserStatusReq.getUserPwd());
            System.out.println("pwd:"+password);
            patchUserStatusReq.setUserPwd(password);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(patchUserStatusReq1.getUserPwd().equals(password)){
            int result = userDao.modifyUserStatus(patchUserStatusReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USEREMAIL);
            }
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }


    public PostReviewRes CreatReview(PostReviewReq postReviewReq) throws BaseException{
        if(userProvider.checkReserveIdx(postReviewReq.getReserveIdx()) ==1){
            throw new BaseException(POST_USERS_EXISTS_RESERVE);
        }
        try{
            System.out.println("CCC4");


            return new PostReviewRes(postReviewReq.getUserIdx(),postReviewReq.getReviewText(),postReviewReq.getReviewRate());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostUserFavRes createUserFav(PostUserFavReq postUserFavReq) throws BaseException{
        if(userProvider.checkHoteIdx(postUserFavReq.getHotelIdx(), postUserFavReq.getUserIdx()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        } // 해당 찜이 이미 존재하는지
        try{
            userDao.createUserFav(postUserFavReq);

            return new PostUserFavRes(postUserFavReq.getHotelIdx(),postUserFavReq.getUserIdx());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostUserCart CreatUserCart(PostUserCart postUserCart) throws BaseException{
        try{
            userDao.createUserCart(postUserCart);

            return new PostUserCart(postUserCart.getUserIdx(),postUserCart.getRoomIdx(),postUserCart.getHotelIdx(),postUserCart.getPriceIdx(),postUserCart.getEntranceTime(),postUserCart.getCheckoutTime());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserCartStatus(PatchUserCart patchUserCart) throws BaseException{
        try{
            System.out.println("service 닉네임");
            int result = userDao.modifyUserCart(patchUserCart);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNICKNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public void deleteUserReview(deleteUserReview deleteUserReview) throws BaseException{
        try{
            System.out.println("service 닉네임");
            int result = userDao.deleteUserReview(deleteUserReview);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNICKNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }


    }

    public void modifyUserPwd(PatchUserPwd patchUserPwd) throws BaseException{
        PatchUserStatusReq patchUserStatusReq = userDao.changePwd_idx(patchUserPwd);
        String password;
        try {
            //암호화
            password= new SHA256().encrypt(patchUserPwd.getUserPwd());
            System.out.println("pwd:"+password);
            patchUserPwd.setUserPwd(password);
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
        if(patchUserStatusReq.getUserPwd().equals(password)){
            String newPwd=new SHA256().encrypt(patchUserPwd.getNewuserPwd());
            String checknewPwd=new SHA256().encrypt(patchUserPwd.getCheckuserPwd());
            int result = userDao.modifyUserPwd(newPwd,patchUserPwd);
            if(newPwd.equals(checknewPwd)){


                patchUserPwd.setUserPwd(newPwd);
                if(result == 0){
                    throw new BaseException(MODIFY_FAIL_PASSWORDERROR);
                }
            }
            else {
                throw new BaseException(MODIFY_FAIL_FIXPASSWORD);
            }
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);}

    }




//    public void modifyUserPwd(PatchUserPwd patchUserPwd,String newuserPwd) throws BaseException{
////        PatchUserStatusReq patchUserStatusReq = userDao.changePwd_idx(patchUserPwd);
//        String password;
//        try {
//            //암호화
//            password= new SHA256().encrypt(patchUserPwd.getUserPwd());
//            System.out.println("pwd:"+password);
//            patchUserPwd.setUserPwd(password);
////            String encryptPwd=new SHA256().encrypt(postUserReq.getUserPwd());
////            postUserReq.setUserPwd(encryptPwd);
//            newuserPwd= new SHA256().encrypt(newuserPwd);
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
//        }
//        if(newuserPwd.equals(patchUserPwd.getUserPwd())){
////            String newPwd=new SHA256().encrypt(patchUserPwd.getNewuserPwd());
////            String checknewPwd=new SHA256().encrypt(patchUserPwd.getCheckuserPwd());
////            String newPassword = new SHA256().encrypt(newuserPwd);
//
//            newuserPwd = new SHA256().encrypt(patchUserPwd.getUserPwd());
//            int result = userDao.modifyUserPwd(newuserPwd,patchUserPwd);
//
//
//                if(result == 0){
//                    throw new BaseException(MODIFY_FAIL_PASSWORDERROR);
//                }
//
//            }else {
//                throw new BaseException(MODIFY_FAIL_PASSWORDERROR);
//            }
//
//
//    }

}
