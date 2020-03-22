package com.tang.kill.domain;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "tb_user")
public class TbUser {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "`password`")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;
}