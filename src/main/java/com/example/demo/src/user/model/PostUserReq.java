package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@Table(name="User")
public class PostUserReq {
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userNickname;
    private String userPwd;
}
