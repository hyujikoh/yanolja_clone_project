package com.example.demo.src.user;

import com.example.demo.src.hotel.model.GetHotelReview;
import com.example.demo.utils.SHA256;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;




    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

//    /** 현재 야놀자에서는 구현 안하는 내용
//     * 회원 조회 API
//     * [GET] /users
//     * 회원 번호 및 이메일 검색 조회 API
//     * [GET] /users? Email=
//     * @return BaseResponse<List<GetUserRes>>
//     */
//    //Query String
//    @ResponseBody
//    @GetMapping("") // (GET) 127.0.0.1:9000/app/users
//    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
//        try{
//            if(Email == null){
//                List<GetUserRes> getUsersRes = userProvider.getUsers();
//                return new BaseResponse<>(getUsersRes);
//            }
//            // Get Users
//            List<GetUserRes> getUsersRes = userProvider.getUsersByEmail(Email);
//            return new BaseResponse<>(getUsersRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

    /**
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    //변수명은 무조건 동일하게!!!!
    @ResponseBody
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:9000/app/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx ){
        // Get Users
        try{
            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/sign-in")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        System.out.println("CCC1");
        if(postUserReq.getUserEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getUserEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        // 전화번호 정규 표현 내가함!
        if(!isRegexPhone(postUserReq.getUserPhone())){

            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }
        //아이디 정규식 내가함!
        if(!isRegexUserName(postUserReq.getUserName())){
            return new BaseResponse<>(POST_USERS_INVALID_USERNAME);
        }
        if(!isRegexPassword(postUserReq.getUserPwd())){
            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
        }
        try{
            System.out.println("CCC2");
            PostUserRes postUserRes = userService.createUser(postUserReq);
            System.out.println("1");
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }





    /**
     * 이메일 로그인 API
     * [POST] /users/logIn/email
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn/email")
    public BaseResponse<PostLoginRes> logInemail(@RequestBody PostLoginReq postLoginReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
//            String encryptPwd;
//            encryptPwd=new SHA256().encrypt(postLoginReq.getUserPwd());
//
//            if(postLoginReq.getUserPwd().equals(encryptPwd)){
//                int userIdx = postLoginReq.getIdx();
//                String jwt = jwtService.createJwt(userIdx);
//                return new PostLoginRes(userIdx,jwt);
//            }
//            else{
//                throw new BaseException(FAILED_TO_LOGIN);
//            }

            System.out.println("이메일 로그인 시작");
            PostLoginRes postLoginRes = userProvider.logIn_email(postLoginReq);
            System.out.println("이메일 로그인 끝");
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn/phone")
    public BaseResponse<PostLoginRes> logInphone(@RequestBody PostLoginPhoneReq postLoginPhoneReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostLoginRes postLoginRes = userProvider.logIn_phone(postLoginPhoneReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }




    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getUserName());
            userService.modifyUserName(patchUserReq);

            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 1. 유저정보변경 API 핸드폰 번호 변경!!! 오현직 메이드
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{Idx}/phone")
    public BaseResponse<String> modifyUserPhone(@PathVariable("Idx") int Idx, @RequestBody User user){
        System.out.println("00");

        try {
//            //jwt에서 idx 추출.
//            System.out.println("시전");
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            System.out.println(userIdxByJwt);
//            if(Idx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            //같다면 유저네임 변경
            PatchUserReq_userPhone patchUserReq_userPhone = new PatchUserReq_userPhone(Idx,user.getUserPhone());
            userService.modifyUserPhone(patchUserReq_userPhone);

            String result = "";
            System.out.println("phone1");
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println("컨트럴러 캐치 에러나옴 ");
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 2. 유저정보변경 API 닉네임 변경!!! 오현직 메이드
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{Idx}/nickname")
    public BaseResponse<String> modifyUserNickname(@PathVariable("Idx") int Idx, @RequestBody User user){
        System.out.println("00");

        try {

            PatchUserNicknameReq patchUserNicknameReq = new PatchUserNicknameReq(Idx,user.getUserNickname());
            userService.modifyUserNickname(patchUserNicknameReq);

            String result = "";
            System.out.println("phone1");
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println("컨트럴러 캐치 에러나옴 ");
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 3. 유저정보변경 API 이메일 변경!!! 오현직 메이드
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{Idx}/email")
    public BaseResponse<String> modifyUserEmail(@PathVariable("Idx") int Idx, @RequestBody User user){
        System.out.println("00");
        try {
            PatchUserEmailReq patchUserEmailReq = new PatchUserEmailReq(Idx,user.getUserEmail());
            userService.modifyUserEmail(patchUserEmailReq);
            String result = "";
            System.out.println("email1");
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println("컨트럴러 캐치 에러나옴 ");
            return new BaseResponse<>((exception.getStatus()));
        }
    }

/* 리뷰 수정 */
    @ResponseBody
    @PatchMapping("/{userIdx}/review")
    public BaseResponse<String> modifyUserReviewText(@PathVariable("userIdx") int userIdx, @RequestBody PatchUserReviewReq user){
        System.out.println("00");
        if(!isRegexReviewLength(user.getReviewText())){
            return new BaseResponse<>(POST_REVIEW_INVALID_TEXTLENGTH);
        }

        try {
            PatchUserReviewReq patchUserReviewReq = new PatchUserReviewReq(userIdx, user.getReviewText(), user.getIdx());
            userService.modifyReviewText(patchUserReviewReq);
            String result = "";
            System.out.println("email1");


            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            System.out.println("컨트럴러 캐치 에러나옴 ");
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** ^^^^^^내가 만든 코드 ^^^^^^^
     유저기준 리뷰 조회
     * @return BaseResponse<List<GetUserRes>> 이것도 수정
     */
    //Query String
    @ResponseBody
    @GetMapping("/{userIdx}/reviews") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserReview>> UserByReviews(@PathVariable("userIdx") int userIdx ) { //
        try{

            List<GetUserReview> getUserReviews = userProvider.getUserReview(userIdx); // 검색조회
            System.out.println("2");
            return new BaseResponse<>(getUserReviews);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }





    /**
     * 4. 유저탈퇴(status ACTIVATE  -> INACTIVATE 로 상태 변경) 오현직 메이드
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
//    @ResponseBody
//    @PatchMapping("/{Idx}/status")
//    public BaseResponse<String> modifyUserStatus(@PathVariable("Idx") int Idx, @RequestBody User user){
//        System.out.println("00");
//        try {
//            PatchUserStatusReq patchUserStatusReq = new PatchUserStatusReq(Idx,user.getUserPwd());
//            userService.modifyUserStatus(patchUserStatusReq);
//            String result = "";
//            System.out.println("상태11");
//            return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            System.out.println("컨트럴러 캐치 에러나옴 ");
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

    /** ^^^^^^내가 만든 코드 ^^^^^^^
     유저기준 장바구니 조회
     * @return BaseResponse<List<GetUserRes>> 이것도 수정
     */
    //Query String
    @ResponseBody
    @GetMapping("/{userIdx}/carts") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserCartReq>> UserByCarts(@PathVariable("userIdx") int userIdx ) { //
        try{

            List<GetUserCartReq> getUserCartReqs = userProvider.getUserCarts(userIdx); // 검색조회
            System.out.println("2");
            return new BaseResponse<>(getUserCartReqs);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @PostMapping("/{userIdx}/review")
    public BaseResponse<PostReviewRes> CreatReview(@RequestBody PostReviewReq postReviewReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.

            if(!isRegexReviewLength(postReviewReq.getReviewText())){
                return new BaseResponse<>(POST_REVIEW_INVALID_TEXTLENGTH);
            }
            System.out.println("이메일 로그인 시작");
            PostReviewRes postReviewRes = userService.CreatReview(postReviewReq);
            System.out.println("이메일 로그인 끝");
            return new BaseResponse<>(postReviewRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /** ^^^^^^내가 만든 코드 ^^^^^^^
      찜리스트 조회
     * @return BaseResponse<List<GetUserRes>> 이것도 수정
     */
    //Query String
    @ResponseBody
    @GetMapping("/{userIdx}/favoritelist") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserFavRes>> UserByFavs(@PathVariable("userIdx") int userIdx ) { //
        try{

            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse(INVALID_USER_JWT);
            }// 무조건

            List<GetUserFavRes> GetUserFavRes = userProvider.GetUserFav(userIdx); // 검색조회
            System.out.println("2");
            return new BaseResponse<>(GetUserFavRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}


//    int userIdxByJwt = jwtService.getUserIdx();
////userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                    return new BaseResponse(INVALID_USER_JWT);
//                    }// 무조건 유저 기준으로 조회 및 할때는 해당 JWT로 토근 생성한다.