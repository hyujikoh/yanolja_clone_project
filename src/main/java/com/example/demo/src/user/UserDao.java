package com.example.demo.src.user;


import com.example.demo.config.BaseResponse;
import com.example.demo.src.hotel.model.GetHotelReview;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.POST_REVIEW_INVALID_TEXTLENGTH;
import static com.example.demo.utils.ValidationRegex.isRegexReviewLength;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select * from UserInfo";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("Idx"),
                        rs.getString("userName"),
                        rs.getString("userpwd"),
                        rs.getString("userNickname"),
                        rs.getString("useremail"),
                        rs.getString("phoneNumber"))
        );
    }

    public List<GetUserRes> getUsersByEmail(String email) {
        String getUsersByEmailQuery = "select * from UserInfo where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("Idx"),
                        rs.getString("userName"),
                        rs.getString("userpwd"),
                        rs.getString("userNickname"),
                        rs.getString("useremail"),
                        rs.getString("phoneNumber")),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int userIdx) {
        String getUserQuery = "select U.Idx,U.userName ,U.userPwd, U.userNickname , U.userEmail ,  U.userPhone  \n" +
                "from User U where Idx = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("Idx"),
                        rs.getString("userName"),
                        rs.getString("userPwd"),
                        rs.getString("userNickname"),
                        rs.getString("userEmail"),
                        rs.getString("userPhone")),
                getUserParams);
    }

    // ???????????? ?????? ??????
    public int createUser(PostUserReq postUserReq) {
        System.out.println("CCC6");
        String createUserQuery = "insert into User (userName,userPhone, userEmail, userNickname, userPwd) VALUES (?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getUserPhone(), postUserReq.getUserEmail(), postUserReq.getUserNickname(), postUserReq.getUserPwd()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        System.out.println("CCC7");
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    // ?????? ??????
    public int checkEmail(String userEmail) {
        String checkEmailQuery = "select exists(select userEmail from User where userEmail = ?)";
        String checkEmailParams = userEmail;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }
    public int checkPhone(String userPhone) {
        String checkPhoneQuery = "select exists(select userPhone from User where userPhone = ?)";
        String checkPhoneParams = userPhone;
        return this.jdbcTemplate.queryForObject(checkPhoneQuery,
                int.class,
                checkPhoneParams);
    }

    public int checkReserveIdx(int reserveIdx) {
        String checkResrveIdxQuery = "select exists(select reserveIdx from Review where reserveIdx = ?)";
        int checkResrveIdxParams = reserveIdx;
        return this.jdbcTemplate.queryForObject(checkResrveIdxQuery,
                int.class,
                checkResrveIdxParams);
    }
    public int checkHotelIdx(int hotelIdx,int userIdx) {
        String checkResrveIdxQuery = "select exists(select hotelIdx, userIdx from FavoritesList where hotelIdx = ? and userIdx=?);";
        int checkResrveIdxParams = hotelIdx;
        int checkResrveIdxParams1 = userIdx;
        return this.jdbcTemplate.queryForObject(checkResrveIdxQuery,
                int.class,
                checkResrveIdxParams,checkResrveIdxParams1);
    }

    // ????????? ??????
    public int modifyUserName(PatchUserReq patchUserReq) {
        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }

    //  ?????? ????????? ??????
    public int modifyUserPhone(PatchUserReq_userPhone patchUserReq_userPhone) {
        String modifyUserPhoneQuery = "update User set userPhone = ? where Idx = ? ";
        Object[] modifyUserPhoneParams = new Object[]{patchUserReq_userPhone.getUserPhone(), patchUserReq_userPhone.getIdx()};

        return this.jdbcTemplate.update(modifyUserPhoneQuery, modifyUserPhoneParams);
    }

    //????????? ?????? ??????
    public int modifyUserNickname(PatchUserNicknameReq patchUserNicknameReq) {
        String modifyUserNicknameQuery = "update User set userNickname = ? where Idx = ? ";
        Object[] modifyUserNicknameParams = new Object[]{patchUserNicknameReq.getUserNickname(), patchUserNicknameReq.getIdx()};

        return this.jdbcTemplate.update(modifyUserNicknameQuery, modifyUserNicknameParams);
    }

    public int modifyUserFavs(PatchUserFav patchUserFav) {
        String modifyUserFavQuery = "update FavoritesList set status='DELETE' where userIdx= ? and hotelIdx =?";


        Object[] modifyUserFavParams = new Object[]{patchUserFav.getUserIdx(),patchUserFav.getHotelIdx()};

        return this.jdbcTemplate.update(modifyUserFavQuery, modifyUserFavParams);
    }

    public int modifyUserCart(PatchUserCart patchUserCart) {
        String modifyUserCartQuery = "update Cart set status='DELETE' where userIdx= ? and Idx =?";


        Object[] modifyUserCartParams = new Object[]{patchUserCart.getUserIdx(),patchUserCart.getIdx()};

        return this.jdbcTemplate.update(modifyUserCartQuery, modifyUserCartParams);
    }
    public int deleteUserReview(deleteUserReview deleteUserReview) {
        String deleteUserReviewQuery = "update Review set status='DELETE' where userIdx= ? and Idx =?";


        Object[] deleteUserReviewParams = new Object[]{deleteUserReview.getUserIdx(),deleteUserReview.getIdx()};

        return this.jdbcTemplate.update(deleteUserReviewQuery, deleteUserReviewParams);

    }
    //?????? ????????? ??????
    public int modifyUserEmail(PatchUserEmailReq patchUserEmailReq) {
        String modifyUserEmailQuery = "update User set userEmail = ? where Idx = ? ";
        Object[] modifyUserEmailParams = new Object[]{patchUserEmailReq.getUserEmail(), patchUserEmailReq.getIdx()};

        return this.jdbcTemplate.update(modifyUserEmailQuery, modifyUserEmailParams);
    }

        //?????? ?????? ?????? 3 !
    public int modifyUserStatus(PatchUserStatusReq patchUserStatusReq) {
        String modifyUserStatusQuery = "update User set status = 'Delete' where Idx = ? ";
        Object[] modifyUserStatusParams = new Object[]{patchUserStatusReq.getIdx()};

        return this.jdbcTemplate.update(modifyUserStatusQuery, modifyUserStatusParams);
    }

    public PatchUserStatusReq changePwd_idx(PatchUserPwd patchUserPwd) {
        String PatchUserStatusQuery = "select Idx,userPwd from User where Idx = ?";
        int PatchUserStatusParams = patchUserPwd.getIdx();

        return this.jdbcTemplate.queryForObject(PatchUserStatusQuery,
                (rs, rowNum) -> new PatchUserStatusReq(
                        rs.getInt("Idx"),
                        rs.getString("userPwd")
                ),
                PatchUserStatusParams
        );
    }
    public PatchUserStatusReq getPwd_idx(PatchUserStatusReq patchUserStatusReq) {
        String PatchUserStatusQuery = "select Idx,userPwd from User where Idx = ?";
        int PatchUserStatusParams = patchUserStatusReq.getIdx();

        return this.jdbcTemplate.queryForObject(PatchUserStatusQuery,
                (rs, rowNum) -> new PatchUserStatusReq(
                        rs.getInt("Idx"),
                        rs.getString("userPwd")
                ),
                PatchUserStatusParams
        );
    }

    //?????? ?????? ?????? ??????
    public int modifyReviewText(PatchUserReviewReq patchUserReviewReq) {
        System.out.println("????????????");
        String modifyReviewTextQuery = "update Review set reviewText = ? where userIdx = ? and Idx = ? ;"; // and ????????? ????????? ??????????????? ????????? ??????.
        Object[] modifyReviewTextParams = new Object[]{patchUserReviewReq.getReviewText(),  patchUserReviewReq.getUserIdx(),patchUserReviewReq.getIdx()};

        return this.jdbcTemplate.update(modifyReviewTextQuery, modifyReviewTextParams);
    }

    // ????????? ?????? ???????????? ??????
    public User getPwd_email(PostLoginReq postLoginReq) {
        String getPwdQuery = "select Idx, userName,userPwd, userEmail,  userPhone,userNickname ,status from User where userEmail = ?";
        String getPwdParams = postLoginReq.getUserEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("Idx"),
                        rs.getString("userName"),
                        rs.getString("userPwd"),
                        rs.getString("userEmail"),
                        rs.getString("userPhone"),
                        rs.getString("userNickname"),
                        rs.getString("status")
                ),
                getPwdParams
        );

    }


    //????????? ?????? ???????????? ??????
    public User getPwd_phone(PostLoginPhoneReq postLoginPhoneReq) {
        String getPwdQuery = "select Idx, userName,userPwd, userEmail,  userPhone,userNickname ,status from User where userPhone = ?";
        String getPwdParams = postLoginPhoneReq.getUserPhone();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("Idx"),
                        rs.getString("userName"),
                        rs.getString("userPwd"),
                        rs.getString("userEmail"),
                        rs.getString("userPhone"),
                        rs.getString("userNickname"),
                        rs.getString("status")
                ),
                getPwdParams
        );

    }



    /*?????? ?????? ?????? ??????*/

    public List<GetUserReview> getUserReviews(int userIdx) {
        String getUserReviewsQuery1 =
                "select review.idx  reviewIdx                               ,\n" +
                        "       review.reviewRate                             ,\n" +
                        "       review.reviewText                             ,\n" +
                        "       R1.reserveidx,\n" +
                        "       R1.roomIdx ,\n" +
                        "       R1.hotelnames,\n" +
                        "       R1.roomType,\n" +
                        "       R1.roomDesc,\n" +
                        "       case when useType = 1 then '??????' else '??????' end Type,\n" +
                        "       RI.reviewImageUrl,\n" +
                        "       case\n" +
                        "           when 24 >= timestampdiff(HOUR, updateAt, current_timestamp)\n" +
                        "               then concat(timestampdiff(HOUR, updateAt, current_timestamp), '?????? ???')\n" +
                        "           else concat(timestampdiff(DAY, updateAt, current_timestamp), '??? ???') end Posteddate\n" +
                        "from Review review\n" +
                        "         left outer join(select reviewIdx, reviewImageUrl  from ReviewImage) RI on (RI.reviewIdx = review.Idx)\n" +
                        "         inner join (select reserve.idx reserveidx , R2.roomIdx, R2.hotelnames , R2.????????? roomType, R2.????????? roomDesc, useType\n" +
                        "                     from Reserve reserve\n" +
                        "                              inner join (Select room.Idx roomIdx, room.hotelIdx, hotel.hotelName hotelnames, room.roomType ?????????, room.roomName ?????????\n" +
                        "                                          from Room room\n" +
                        "                                                   inner join(select H.hotelName , H.idx from Hotel H) hotel\n" +
                        "                                                             on (hotel.idx = room.hotelIdx)) R2\n" +
                        "                                         on (reserve.roomIdx = R2.roomIdx)\n" +
                        "                     where progress = 2) R1 on (review.reserveIdx = R1.reserveidx)\n" +
                        "where review.userIdx = ?;";
        int getUserReviewsParams = userIdx;
        return this.jdbcTemplate.query(getUserReviewsQuery1,
                (rs, rowNum) -> new GetUserReview(
                        rs.getInt("reviewIdx"),
                        rs.getInt("reviewRate"),
                        rs.getString("reviewText"),
                        rs.getInt("reserveidx"),
                        rs.getInt("roomIdx"),
                        rs.getString("hotelnames"),
                        rs.getString("roomType"),
                        rs.getString("roomDesc"),
                        rs.getString("Type"),
                        rs.getString("reviewImageUrl"),
                        rs.getString("Posteddate")),
                getUserReviewsParams);
    }


    // ???????????? ???????????? ??????
    public List<GetUserCartReq> getUserCarts(int userIdx) {
        String getUserCartsQuery1 = "select Idx,\n" +
                "       date_format(entranceTime,'%H : %i') entrance,\n" +
                "       date_format(checkoutTime,'%H : %i') checktime,\n" +
                "       imageUrl,\n" +
                "       R1.hotelName ,\n" +
                "       R1.hotelLocationDesc,\n" +
                "       R2.roomType,\n" +
                "       R2.roomName,\n" +
                "       R2.allowedPeople,\n" +
                "       R2.maxPeople,\n" +
                "       R1.couponava,\n" +
                "       R1.couponprices,\n" +
                "       R2.reserveprice,\n" +
                "       R2.stayprice_discount\n" +
                "from Cart C\n" +
                "         inner join (select hotelIdx I_hotelIdx, imageUrl  from HotelImage WHERE type = 1) I1\n" +
                "                    on (I1.I_hotelIdx = C.hotelIdx)\n" +
                "         inner join (select Idx                                                      ???????????????,\n" +
                "                            hotelName                                                ,\n" +
                "                            hotelLocationDesc                                        ,\n" +
                "                            couponName,\n" +
                "                            case when couponCount = 0 then '??????' else couponCount end couponava\n" +
                "                             ,\n" +
                "                            couponprices\n" +
                "                     from Hotel\n" +
                "                              left outer join (select Idx                                  ???????????????,\n" +
                "                                                      hotelIdx,\n" +
                "                                                      couponCount,\n" +
                "                                                      couponName,\n" +
                "                                                      case\n" +
                "                                                          when disPercent is null then concat(min(disprice), '???')\n" +
                "                                                          else concat(disPercent, '%') end couponprices\n" +
                "                                               from coupon\n" +
                "                                               group by hotelIdx) C1 on (Hotel.Idx = C1.hotelIdx)) R1\n" +
                "                    on (R1.??????????????? = C.hotelIdx)\n" +
                "         inner join(select Idx                                             ????????????,\n" +
                "                           roomType                                        ,\n" +
                "                           roomName                                        ,\n" +
                "                           allowedPeople                                   ,\n" +
                "                           maxPeople                                       ,\n" +
                "                           case when roomCount = 0 then '????????????' else ?????? end reserveprice,\n" +
                "                           stayprice_discount,\n" +
                "                           case\n" +
                "                               when dayuseCount = 0 then '????????????'\n" +
                "                               else ???????????? end                               ?????????,\n" +
                "                           ??????????????????,\n" +
                "                           entranceTime                                    ????????????,\n" +
                "                           ????????????\n" +
                "\n" +
                "                    from Room R0\n" +
                "                             left outer join(select dayuseCount,\n" +
                "                                                    dayuseStart ????????????,\n" +
                "                                                    dayuseCount ??????????????????,\n" +
                "                                                    roomIdx     ??????_????????????\n" +
                "                                             from RoomDayuse) RD\n" +
                "                                            on (RD.??????_???????????? = R0.Idx)\n" +
                "                             inner join (select roomdx, ???????????? ??????, ?????????????????? as stayprice_discount, ????????????, ??????????????????\n" +
                "                                         from Price\n" +
                "                                         where case\n" +
                "                                                   when dayofweek(now()) = (6 or 7) then dayType = 2\n" +
                "                                                   else dayType = 1 end\n" +
                "                    ) R on (R.roomdx = R0.Idx)) R2\n" +
                "                   on (R2.???????????? = C.roomIdx)\n" +
                "where userIdx = ?;";
        int getUserCartsParams = userIdx;
        return this.jdbcTemplate.query(getUserCartsQuery1,
                (rs, rowNum) -> new GetUserCartReq(
                        rs.getInt("Idx"),
                        rs.getString("entrance"),
                        rs.getString("checktime"),
                        rs.getString("imageUrl"),
                        rs.getString("hotelName"),
                        rs.getString("hotelLocationDesc"),
                        rs.getString("roomType"),
                        rs.getString("roomName"),
                        rs.getString("allowedPeople"),
                        rs.getString("maxPeople"),
                        rs.getString("couponava"),
                        rs.getString("couponprices"),
                        rs.getString("reserveprice"),
                        rs.getInt("stayprice_discount")),
                getUserCartsParams);
    }

    /*public User getPwd_email(PostLoginReq postLoginReq) {
        String getPwdQuery = "select Idx, userName,userPwd, userEmail,  userPhone,userNickname ,status from User where userEmail = ?";
        String getPwdParams = postLoginReq.getUserEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("Idx"),
                        rs.getString("userName"),
                        rs.getString("userPwd"),
                        rs.getString("userEmail"),
                        rs.getString("userPhone"),
                        rs.getString("userNickname"),
                        rs.getString("status")
                ),
                getPwdParams
        );

    }*/
    public int createuserreview(PostReviewReq postReviewReq) {
        String getcreatereviewQuery = "insert into Review (reviewRate, reviewText, reserveIdx, userIdx, rev1, rev2,rev3,rev4) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";

        //reviewRate, reviewText, reserveIdx, userIdx, rev1, rev2,rev3,rev4
        Object[] getcreatereviewParams = new Object[]{postReviewReq.getReviewRate(), postReviewReq.getReviewText(), postReviewReq.getReserveIdx(),postReviewReq.getUserIdx(), postReviewReq.getRev1(), postReviewReq.getRev2(), postReviewReq.getRev3(), postReviewReq.getRev4()};

        this.jdbcTemplate.update(getcreatereviewQuery, getcreatereviewParams);

        String lastInserIdQuery = "select last_insert_id()";
        System.out.println("CCC7");
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);

    }


    public List<GetUserFavRes> getUserFav(int userIdx) {
        String GetUserFavsQuery1 ="select R3.hotelName,\n" +
                "       R3.grade,\n" +
                "       R3.hotelOpt,\n" +
                "       R3.auth,\n" +
                "       R3.hotelLocation,\n" +
                "       reviewcnt,\n" +
                "       reviewavg,\n" +
                "       F.hotelIdx,\n" +
                "       stayprice,\n" +
                "       staydiscount,\n" +
                "       date_format(????????????, '%H : %i') as stayentrance,\n" +
                "       dayuseprice,\n" +
                "       dayusediscount,\n" +
                "       date_format(????????????, '%H : %i') as dayuseentrance,\n" +
                "       couponava,\n" +
                "       couponprice,\n" +
                "       imageUrl,\n" +
                "       userIdx\n" +
                "from FavoritesList F\n" +
                "         inner join (select Idx                                                      ???????????????,\n" +
                "                            hotelName                                                ????????????,\n" +
                "                            hotelLocationDesc                                        ??????????????????,\n" +
                "                            couponName,\n" +
                "                            case when couponCount = 0 then '??????' else couponCount end couponava,\n" +
                "                            couponprice,\n" +
                "                            imageUrl\n" +
                "                     from Hotel\n" +
                "                              inner join (select hotelIdx I_hotelIdx, imageUrl from HotelImage WHERE type = 1) I1\n" +
                "                                         on (I1.I_hotelIdx = Hotel.Idx)\n" +
                "                              left outer join (select Idx                                  ???????????????,\n" +
                "                                                      hotelIdx,\n" +
                "                                                      couponCount,\n" +
                "                                                      couponName,\n" +
                "                                                      case\n" +
                "                                                          when disPercent is null then concat(min(disprice), '???')\n" +
                "                                                          else concat(disPercent, '%') end couponprice\n" +
                "                                               from coupon\n" +
                "                                               group by hotelIdx) C1 on (Hotel.Idx = C1.hotelIdx)) R1\n" +
                "                    on (R1.??????????????? = F.hotelIdx)\n" +
                "         inner join (select H.grade,\n" +
                "                            H.auth,\n" +
                "                            H.idx,\n" +
                "                            H.hotelName,\n" +
                "                            H.hotelOpt,\n" +
                "                            H.hotelLocation,\n" +
                "                            stayprice,\n" +
                "                            ????????? dayuseprice,\n" +
                "                            staydiscount,\n" +
                "                            dayusediscount,\n" +
                "                            ????????????,\n" +
                "                            ????????????\n" +
                "                     from Hotel H\n" +
                "                              inner join (select idx,\n" +
                "                                                 hotelIdx,\n" +
                "                                                 roomName,\n" +
                "                                                 roomType,\n" +
                "                                                 case when roomCount = 0 then '????????????' else ?????? end stayprice,\n" +
                "                                                 ??????????????????                                          staydiscount,\n" +
                "                                                 case\n" +
                "                                                     when dayuseCount = 0 then '????????????'\n" +
                "                                                     else ???????????? end                               ?????????,\n" +
                "                                                 ??????????????????                                          dayusediscount,\n" +
                "                                                 entranceTime                                    ????????????,\n" +
                "                                                 ????????????\n" +
                "                                          from Room R0\n" +
                "                                                   left outer join(select dayuseCount,\n" +
                "                                                                          dayuseStart ????????????,\n" +
                "                                                                          dayuseCount ??????????????????,\n" +
                "                                                                          roomIdx     ??????_????????????\n" +
                "                                                                   from RoomDayuse) RD\n" +
                "                                                                  on (RD.??????_???????????? = R0.Idx)\n" +
                "                                                   inner join (select roomdx, ???????????? ??????, ??????????????????, ????????????, ??????????????????\n" +
                "                                                               from Price\n" +
                "                                                               where case\n" +
                "                                                                         when dayofweek(now()) = (6 or 7)\n" +
                "                                                                             then dayType = 2\n" +
                "                                                                         else dayType = 1 end\n" +
                "                                          ) R on (R.roomdx = R0.Idx)) R2 on (R2.hotelIdx = H.Idx)\n" +
                "                     group by H.Idx) R3 on (F.hotelIdx = R3.Idx)\n" +
                "         inner join (select count(*) as reviewcnt, round(avg(reviewRate), 1) as reviewavg, hotelIdx\n" +
                "                     from Review Rev\n" +
                "                              inner join (select Idx, hotelIdx from Reserve) Reserve on (Reserve.Idx = Rev.reserveIdx)\n" +
                "                     group by hotelIdx) R2 on (F.hotelIdx = R2.hotelIdx)\n" +
                "where userIdx = ?;";
        int getUserFavsParams = userIdx;
        return this.jdbcTemplate.query(GetUserFavsQuery1,
                (rs, rowNum) -> new GetUserFavRes(
                        rs.getString("hotelName"),
                        rs.getString("grade"),
                        rs.getInt("hotelOpt"),
                        rs.getInt("auth"),
                        rs.getString("hotelLocation"),
                        rs.getInt("reviewcnt"),
                        rs.getFloat("reviewavg"),
                        rs.getInt("hotelIdx"),
                        rs.getString("stayprice"),
                        rs.getInt("staydiscount"),
                        rs.getString("stayentrance"),
                        rs.getString("dayuseprice"),
                        rs.getInt("dayusediscount"),
                        rs.getString("dayuseentrance"),
                        rs.getString("couponava"),
                        rs.getString("couponprice"),
                        rs.getString("imageUrl"),
                        rs.getInt("userIdx")),
                getUserFavsParams);
    }


    public int createUserFav(PostUserFavReq postUserFavReq) {
        String getCreateUserFavQuery = "insert into FavoritesList (hotelIdx,userIdx) values (?,?);";
        Object[] getcreaterFavesParams = new Object[] {postUserFavReq.getHotelIdx(),postUserFavReq.getUserIdx()};
        this.jdbcTemplate.update(getCreateUserFavQuery, getcreaterFavesParams);
        String lastInserIdQuery = "select last_insert_id()";
        System.out.println("CCC7");
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
    public int createUserCart(PostUserCart postUserCart) {
        String getCreateUserCartQuery = "insert into Cart (userIdx,roomIdx,hotelIdx,priceIdx,entranceTime,checkoutTime) values (?,?,?,?,?,?);";
        Object[] getCreateUserCartParams = new Object[] {postUserCart.getUserIdx(),postUserCart.getRoomIdx(),postUserCart.getHotelIdx(),postUserCart.getPriceIdx(),postUserCart.getEntranceTime(),postUserCart.getCheckoutTime()};
        this.jdbcTemplate.update(getCreateUserCartQuery, getCreateUserCartParams);
        String lastInserIdQuery = "select last_insert_id()";
        System.out.println("CCC7");
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);


    }

    public int modifyUserPwd(String newuserPwd,PatchUserPwd patchUserPwd) {
        String modifyUserPwdQuery = "update User set userPwd = ? where userPwd =? and Idx = ? ";
        Object[] modifyUserPwdParams = new Object[]{newuserPwd,patchUserPwd.getUserPwd(),patchUserPwd.getIdx()};

        return this.jdbcTemplate.update(modifyUserPwdQuery, modifyUserPwdParams);
    }




//    public int modifyUserPwd(String newuserPwd,PatchUserPwd patchUserPwd) {
//        String modifyUserPwdQuery = "update User set userPwd = ? where userPwd =? and Idx = ? ";
//        Object[] modifyUserPwdParams = new Object[]{newuserPwd,patchUserPwd.getUserPwd(),patchUserPwd.getIdx()};
//        System.out.println("123");
//        return this.jdbcTemplate.update(modifyUserPwdQuery, modifyUserPwdParams);
//    }
}

