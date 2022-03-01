package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {

    private String id;
    private String UserName;
    private String userBirth;
    private String email;
    private String password;
    private String phoneNumer;
}
