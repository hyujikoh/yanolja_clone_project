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


    public int createUser(PostUserReq postUserReq) {
        System.out.println("CCC6");
        String createUserQuery = "insert into User (userName,userPhone, userEmail, userNickname, userPwd) VALUES (?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getUserPhone(), postUserReq.getUserEmail(), postUserReq.getUserNickname(), postUserReq.getUserPwd()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        System.out.println("CCC7");
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    public int checkEmail(String userEmail) {
        String checkEmailQuery = "select exists(select userEmail from User where userEmail = ?)";
        String checkEmailParams = userEmail;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int modifyUserName(PatchUserReq patchUserReq) {
        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }

    // 내가 만듬 패치 1 !
    public int modifyUserPhone(PatchUserReq_userPhone patchUserReq_userPhone) {
        String modifyUserPhoneQuery = "update User set userPhone = ? where Idx = ? ";
        Object[] modifyUserPhoneParams = new Object[]{patchUserReq_userPhone.getUserPhone(), patchUserReq_userPhone.getIdx()};

        return this.jdbcTemplate.update(modifyUserPhoneQuery, modifyUserPhoneParams);
    }

    //내가 만든 패치 2 !
    public int modifyUserNickname(PatchUserNicknameReq patchUserNicknameReq) {
        String modifyUserNicknameQuery = "update User set userNickname = ? where Idx = ? ";
        Object[] modifyUserNicknameParams = new Object[]{patchUserNicknameReq.getUserNickname(), patchUserNicknameReq.getIdx()};

        return this.jdbcTemplate.update(modifyUserNicknameQuery, modifyUserNicknameParams);
    }

    //내가 만든 패치 2 !
    public int modifyUserEmail(PatchUserEmailReq patchUserEmailReq) {
        String modifyUserEmailQuery = "update User set userEmail = ? where Idx = ? ";
        Object[] modifyUserEmailParams = new Object[]{patchUserEmailReq.getUserEmail(), patchUserEmailReq.getIdx()};

        return this.jdbcTemplate.update(modifyUserEmailQuery, modifyUserEmailParams);
    }

    //내가 만든 패치 3 !
//    public int modifyUserStatus(PatchUserStatusReq patchUserStatusReq) {
//        String modifyUserStatusQuery = "update User set status = 'INACTIVATE' where Idx = ? ";
//        Object[] modifyUserStatusParams = new Object[]{patchUserStatusReq.getIdx()};
//
//        return this.jdbcTemplate.update(modifyUserStatusQuery, modifyUserStatusParams);
//    }
//    내가 만든 패치 3 !
    public int modifyReviewText(PatchUserReviewReq patchUserReviewReq)   {
        System.out.println("리뷰수정");
        String modifyReviewTextQuery = "update Review set reviewText = ? where userIdx = ? and Idx = ? ;"; // and 연산자 사용시 우선순위는 맨뒤에 있다.
        Object[] modifyReviewTextParams = new Object[]{patchUserReviewReq.getReviewText(), patchUserReviewReq.getIdx(),patchUserReviewReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyReviewTextQuery, modifyReviewTextParams);
    }
// 이메일 기준 패스워드 조회
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

/*특정 유저 기준 리뷰*/

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
                        "       case when useType = 1 then '숙박' else '대실' end Type,\n" +
                        "       RI.reviewImageUrl,\n" +
                        "       case\n" +
                        "           when 24 >= timestampdiff(HOUR, updateAt, current_timestamp)\n" +
                        "               then concat(timestampdiff(HOUR, updateAt, current_timestamp), '시간 전')\n" +
                        "           else concat(timestampdiff(DAY, updateAt, current_timestamp), '일 전') end Posteddate\n" +
                        "from Review review\n" +
                        "         left outer join(select reviewIdx, reviewImageUrl  from ReviewImage) RI on (RI.reviewIdx = review.Idx)\n" +
                        "         inner join (select reserve.idx reserveidx , R2.roomIdx, R2.hotelnames , R2.방타입 roomType, R2.방상세 roomDesc, useType\n" +
                        "                     from Reserve reserve\n" +
                        "                              inner join (Select room.Idx roomIdx, room.hotelIdx, hotel.hotelName hotelnames, room.roomType 방타입, room.roomName 방상세\n" +
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
}
