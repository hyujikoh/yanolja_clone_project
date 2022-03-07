package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@Entity

@Table(name = "User")
public class GetUserRes {
    @Id
    @Column(name="Idx")
    private int userIdx;
    private String userName;
    private String userPwd;
    private String userNickname;
    private String userEmail;
    private String userPhone;

    public GetUserRes() {

    }
}
