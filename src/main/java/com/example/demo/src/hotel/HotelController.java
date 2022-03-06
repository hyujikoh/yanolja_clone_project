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

    /** ^^^^^^전 코드^^^^^^
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
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

    /**
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
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

//    /**
//     * 회원가입 API
//     * [POST] /users
//     * @return BaseResponse<PostUserRes>
//     */
//    // Body
//    @ResponseBody
//    @PostMapping("/sign-in")
//    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
//        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
//        System.out.println("CCC1");
//        if(postUserReq.getUserEmail() == null){
//            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
//        }
//        //이메일 정규표현
//        if(!isRegexEmail(postUserReq.getUserEmail())){
//            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
//        }
//
//        // 전화번호 정규 표현 내가함!
//        if(!isRegexPhone(postUserReq.getUserPhone())){
//
//            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
//        }
//        //아이디 정규식 내가함!
//        if(!isRegexUserName(postUserReq.getUserName())){
//            return new BaseResponse<>(POST_USERS_INVALID_USERNAME);
//        }
//        if(!isRegexPassword(postUserReq.getUserPwd())){
//            return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
//        }
//        try{
//            System.out.println("CCC2");
//            PostUserRes postUserRes = hotelService.createUser(postUserReq);
//            System.out.println("1");
//            return new BaseResponse<>(postUserRes);
//        } catch(BaseException exception){
//            return new BaseResponse<>((exception.getStatus()));
//        }
//
//    }
//    /**
//     * 로그인 API
//     * [POST] /users/logIn
//     * @return BaseResponse<PostLoginRes>
//     */
//    @ResponseBody
//    @PostMapping("/logIn")
//    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
//        try{
//            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
//            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
//            PostLoginRes postLoginRes = hotelProvider.logIn(postLoginReq);
//            return new BaseResponse<>(postLoginRes);
//        } catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
//
//    /**
//     * 유저정보변경 API
//     * [PATCH] /users/:userIdx
//     * @return BaseResponse<String>
//     */
//    @ResponseBody
//    @PatchMapping("/{userIdx}")
//    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){
//        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
//            //같다면 유저네임 변경
//            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getUserName());
//            userService.modifyUserName(patchUserReq);
//
//            String result = "";
//        return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 1. 유저정보변경 API 핸드폰 번호 변경!!! 오현직 메이드
//     * [PATCH] /users/:userIdx
//     * @return BaseResponse<String>
//     */
//    @ResponseBody
//    @PatchMapping("/{Idx}/phone")
//    public BaseResponse<String> modifyUserPhone(@PathVariable("Idx") int Idx, @RequestBody User user){
//        System.out.println("00");
//
//        try {
//            //jwt에서 idx 추출.
//            System.out.println("시전");
////            int userIdxByJwt = jwtService.getUserIdx();
////            //userIdx와 접근한 유저가 같은지 확인
////            System.out.println(userIdxByJwt);
////            if(userIdx != userIdxByJwt){
////                return new BaseResponse<>(INVALID_USER_JWT);
////            }
//            //같다면 유저네임 변경
//            PatchUserReq_userPhone patchUserReq_userPhone = new PatchUserReq_userPhone(Idx,user.getUserPhone());
//            userService.modifyUserPhone(patchUserReq_userPhone);
//
//            String result = "";
//            System.out.println("phone1");
//            return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            System.out.println("컨트럴러 캐치 에러나옴 ");
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 2. 유저정보변경 API 닉네임 변경!!! 오현직 메이드
//     * [PATCH] /users/:userIdx
//     * @return BaseResponse<String>
//     */
//    @ResponseBody
//    @PatchMapping("/{Idx}/nickname")
//    public BaseResponse<String> modifyUserNickname(@PathVariable("Idx") int Idx, @RequestBody User user){
//        System.out.println("00");
//
//        try {
//
//            PatchUserNicknameReq patchUserNicknameReq = new PatchUserNicknameReq(Idx,user.getUserNickname());
//            userService.modifyUserNickname(patchUserNicknameReq);
//
//            String result = "";
//            System.out.println("phone1");
//            return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            System.out.println("컨트럴러 캐치 에러나옴 ");
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//    /**
//     * 3. 유저정보변경 API 이메일 변경!!! 오현직 메이드
//     * [PATCH] /users/:userIdx
//     * @return BaseResponse<String>
//     */
//    @ResponseBody
//    @PatchMapping("/{Idx}/email")
//    public BaseResponse<String> modifyUserEmail(@PathVariable("Idx") int Idx, @RequestBody User user){
//        System.out.println("00");
//        try {
//            PatchUserEmailReq patchUserEmailReq = new PatchUserEmailReq(Idx,user.getUserEmail());
//            userService.modifyUserEmail(patchUserEmailReq);
//            String result = "";
//            System.out.println("email1");
//            return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            System.out.println("컨트럴러 캐치 에러나옴 ");
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
//
//
//
//    /**
//     * 4. 유저탈퇴(status ACTIVATE  -> INACTIVATE 로 상태 변경) 오현직 메이드
//     * [PATCH] /users/:userIdx
//     * @return BaseResponse<String>
//     */
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


}
