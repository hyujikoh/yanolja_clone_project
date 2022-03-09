package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor

public class PatchUserPwd {
    private int Idx;
    private String userPwd;
    private String checkuserPwd;
    private String newuserPwd;
}
