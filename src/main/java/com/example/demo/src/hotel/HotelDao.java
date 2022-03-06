package com.example.demo.src.hotel;


import com.example.demo.src.hotel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class HotelDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetHotelResConditoin> getHotels() {
        System.out.println("다오에러 전체조회");
        String getHotelByhotelNameQuery =
                "select H.Idx,\n" +
                        "       H.hotelName  ,\n" +
                        "       H.hotelLocation,\n" +
                        "       H.hotelOpt   ,\n" +
                        "       H.auth       ,\n" +
                        "       imageUrl   ,\n" +
                        "       reviewcount,\n" +
                        "       reviewavg,\n" +
                        "       stayprice,\n" +
                "       staypricediscount,\n" +
                        "       dayuseprice,\n" +
                "       dayusepricediscount,\n" +
                        "       H.hotelDesc  \n" +
                        "from Hotel H\n" +
                        "         inner join (select * from HotelImage WHERE type = 1) R1 on (R1.hotelIdx = H.Idx)\n" +
                        "         inner join (select count(*) as reviewcount, round(avg(reviewRate), 1) as reviewavg, hotelIdx 호텔번호\n" +
                        "                     from Review Rev\n" +
                        "                              inner join (select Idx, hotelIdx from Reserve) Reserve on (Reserve.Idx = Rev.reserveIdx)\n" +
                        "                     group by hotelIdx) R2 on (H.Idx = R2.호텔번호)\n" +
                        "\n" +
                        "         inner join (select idx,\n" +
                        "                            roomName,\n" +
                        "                            roomType,\n" +
                        "                            case when roomCount = 0 then '예약마감' else 가격 end  as stayprice,\n" +
                        "                            staypricediscount,\n" +
                        "                            대실가격 as dayuseprice,\n" +
                        "                            dayusepricediscount,\n" +
                        "                            hotelIdx\n" +
                        "                     from Room R0\n" +
                        "                              left outer join (select roomdx, 숙박가격 가격, 숙박할인가격 as staypricediscount, 대실가격, 대실할인가격 as dayusepricediscount  \n" +
                        "                                               from Price\n" +
                        "                                               where case\n" +
                        "                                                         when dayofweek(now()) = (6 or 7) then dayType = 2\n" +
                        "                                                         else dayType = 1 end\n" +
                        "                     ) R on (R.roomdx = R0.Idx)) R3 on (R3.hotelIdx = H.Idx)\n" +
                        "group by H.Idx;";
        return this.jdbcTemplate.query(getHotelByhotelNameQuery,
                (rs, rowNum) -> new GetHotelResConditoin(
                        rs.getInt("Idx"),
                        rs.getString("hotelName"),
                        rs.getString("hotelLocation"),
                        rs.getInt("hotelOpt"),
                        rs.getInt("auth"),
                        rs.getString("imageUrl"),
                        rs.getInt("reviewcount"),
                        rs.getFloat("reviewavg"),
                        rs.getString("stayprice"),
                        rs.getInt("staypricediscount"),
                        rs.getString("dayuseprice"),
                        rs.getInt("dayusepricediscount"),
                        rs.getString("hotelDesc"))
        );
    }

    public List<GetHotelResConditoin> HotelByhotelName(String hotelName) {
        String getHotelByhotelNameQuery1 =
        "select H.Idx,\n" +
                "       H.hotelName  ,\n" +
                "       H.hotelLocation,\n" +
                "       H.hotelOpt   ,\n" +
                "       H.auth       ,\n" +
                "       imageUrl   ,\n" +
                "       reviewcount,\n" +
                "       reviewavg,\n" +
                "       stayprice,\n" +
                "       staypricediscount,\n" +
                "       dayuseprice,\n" +
                "       dayusepricediscount,\n" +
                "       H.hotelDesc  \n" +
                "from Hotel H\n" +
                "         inner join (select * from HotelImage WHERE type = 1) R1 on (R1.hotelIdx = H.Idx)\n" +
                "         inner join (select count(*) as reviewcount, round(avg(reviewRate), 1) as reviewavg, hotelIdx 호텔번호\n" +
                "                     from Review Rev\n" +
                "                              inner join (select Idx, hotelIdx from Reserve) Reserve on (Reserve.Idx = Rev.reserveIdx)\n" +
                "                     group by hotelIdx) R2 on (H.Idx = R2.호텔번호)\n" +
                "\n" +
                "         inner join (select idx,\n" +
                "                            roomName,\n" +
                "                            roomType,\n" +
                "                            case when roomCount = 0 then '예약마감' else 가격 end  as stayprice,\n" +
                "                            staypricediscount,\n" +
                "                            대실가격 as dayuseprice,\n" +
                "                            dayusepricediscount,\n" +
                "                            hotelIdx\n" +
                "                     from Room R0\n" +
                "                              left outer join (select roomdx, 숙박가격 가격, 숙박할인가격 as staypricediscount, 대실가격, 대실할인가격 as dayusepricediscount\n" +
                "                                               from Price\n" +
                "                                               where case\n" +
                "                                                         when dayofweek(now()) = (6 or 7) then dayType = 2\n" +
                "                                                         else dayType = 1 end\n" +
                "                     ) R on (R.roomdx = R0.Idx)) R3 on (R3.hotelIdx = H.Idx)\n" +
                "where hotelName like concat('%',?,'%')\n" +
                "group by H.Idx;";
        String getHotelByhotelNameParams = hotelName;
        System.out.println("다오에러");
        return this.jdbcTemplate.query(getHotelByhotelNameQuery1,
                (rs, rowNum) -> new GetHotelResConditoin(
                        rs.getInt("Idx"),
                        rs.getString("hotelName"),
                        rs.getString("hotelLocation"),
                        rs.getInt("hotelOpt"),
                        rs.getInt("auth"),
                        rs.getString("imageUrl"),
                        rs.getInt("reviewcount"),
                        rs.getFloat("reviewavg"),
                        rs.getString("stayprice"),
                        rs.getInt("staypricediscount"),
                        rs.getString("dayuseprice"),
                        rs.getInt("dayusepricediscount"),
                        rs.getString("hotelDesc")),
                getHotelByhotelNameParams);
    }

    // 유저 조회 를 호텔 인덱스 기준으로 조회하게 한다.
    public GetHotelRes getHotel(int hotelIdx) {
        String getHotelQuery = "select H.Idx ,H.hotelName 이름 ,H.hotelLocationDesc asd, H.hotelType 김, H.locationType ,  H.hotelDesc  \n" +
                "from Hotel H where Idx = ?"; // 수정해라!!!!!!!
        int getHotelParams = hotelIdx;
        System.out.println("호텔1개 검색 전전");
        return this.jdbcTemplate.queryForObject(getHotelQuery,
                (rs, rowNum) -> new GetHotelRes(
                        rs.getInt("Idx"),
                        rs.getString("이름"),
                        rs.getString("asd"),
                        rs.getString("locationType"),
                        rs.getString("hotelDesc")),
                getHotelParams);
    }


//    public int createUser(PostUserReq postUserReq) {
//        System.out.println("CCC6");
//        String createUserQuery = "insert into User (userName,userPhone, userEmail, userNickname, userPwd) VALUES (?,?,?,?,?)";
//        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getUserPhone(), postUserReq.getUserEmail(), postUserReq.getUserNickname(), postUserReq.getUserPwd()};
//        this.jdbcTemplate.update(createUserQuery, createUserParams);
//
//        String lastInserIdQuery = "select last_insert_id()";
//        System.out.println("CCC7");
//        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
//    }

    public int checkEmail(String userEmail) {
        String checkEmailQuery = "select exists(select userEmail from User where userEmail = ?)";
        String checkEmailParams = userEmail;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

//    public int modifyUserName(PatchUserReq patchUserReq) {
//        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
//        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};
//
//        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
//    }
//// 내가 만듬 패치 1 !
//    public int modifyUserPhone(PatchUserReq_userPhone patchUserReq_userPhone) {
//        String modifyUserPhoneQuery = "update User set userPhone = ? where Idx = ? ";
//        Object[] modifyUserPhoneParams = new Object[]{patchUserReq_userPhone.getUserPhone(), patchUserReq_userPhone.getIdx()};
//
//        return this.jdbcTemplate.update(modifyUserPhoneQuery, modifyUserPhoneParams);
//    }
//    //내가 만든 패치 2 !
//    public int modifyUserNickname(PatchUserNicknameReq patchUserNicknameReq) {
//        String modifyUserNicknameQuery = "update User set userNickname = ? where Idx = ? ";
//        Object[] modifyUserNicknameParams = new Object[]{patchUserNicknameReq.getUserNickname(), patchUserNicknameReq.getIdx()};
//
//        return this.jdbcTemplate.update(modifyUserNicknameQuery, modifyUserNicknameParams);
//    }
//    //내가 만든 패치 2 !
//    public int modifyUserEmail(PatchUserEmailReq patchUserEmailReq) {
//        String modifyUserEmailQuery = "update User set userEmail = ? where Idx = ? ";
//        Object[] modifyUserEmailParams = new Object[]{patchUserEmailReq.getUserEmail(), patchUserEmailReq.getIdx()};
//
//        return this.jdbcTemplate.update(modifyUserEmailQuery, modifyUserEmailParams);
//    }
//
//    //내가 만든 패치 3 !
//    public int modifyUserStatus(PatchUserStatusReq patchUserStatusReq) {
//        String modifyUserStatusQuery = "update User set status = 'INACTIVATE' where Idx = ? ";
//        Object[] modifyUserStatusParams = new Object[]{patchUserStatusReq.getIdx()};
//
//        return this.jdbcTemplate.update(modifyUserStatusQuery, modifyUserStatusParams);
//    }


//    public User getPwd(PostLoginReq postLoginReq) {
//        String getPwdQuery = "select userIdx, password,email,userName,ID from UserInfo where ID = ?";
//        String getPwdParams = postLoginReq.getId();
//
//        return this.jdbcTemplate.queryForObject(getPwdQuery,
//                (rs, rowNum) -> new User(
//                        rs.getInt("userIdx"),
//                        rs.getString("userName"),
//                        rs.getString("userPwd"),
//                        rs.getString("userEmail"),
//                        rs.getString("userPhone"),
//                        rs.getString("userNickname"),
//                        rs.getString("status")
//                ),
//                getPwdParams
//        );
//
//    }


}
