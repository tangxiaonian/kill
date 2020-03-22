package com.tang.kill.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "tb_shop")
public class TbShop {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "counts")
    private String counts;

    @Column(name = "buyTime")
    private Date buytime;

    /**
     * 是否有效
     */
    @Column(name = "isactive")
    private String isactive;
}