package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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



    public User getPwd(PostLoginReq postLoginReq) {
        String getPwdQuery = "select userIdx, password,email,userName,ID from UserInfo where ID = ?";
        String getPwdParams = postLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userIdx"),
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


}
