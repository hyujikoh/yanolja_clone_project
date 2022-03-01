package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int Idx;
    private String userName;
    private String password;
    private String userNickname;
    private String email;
    private String phoneNumber;
}
