package com.son.auth.example.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "tb_user")
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "user_role")
    private String userRole;


    @Builder
    User(String userId, String userPw, String userRole) {
        this.userId = userId;
        this.userPw = userPw;
        this.userRole = userRole;
    }
}
