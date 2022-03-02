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

@Table(name="User")
@Entity
public class User {
    @Id
//    @Column(name="Idx")
    private int Idx;

    private String userName;
    private String userPwd;
    private String userEmail;
    private String userPhone;
    private String userNickname;
    public User() {

    }
}
