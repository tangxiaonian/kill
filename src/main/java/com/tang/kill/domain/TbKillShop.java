package com.tang.kill.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "tb_kill_shop")
public class TbKillShop {
    @Id
    @Column(name = "k_id")
    @GeneratedValue(generator = "JDBC")
    private Integer kId;

    @Column(name = "s_id")
    private Integer sId;

    /**
     * 可以被秒杀的商品数
     */
    @Column(name = "counts")
    private Integer counts;

    @Column(name = "k_start_time")
    private Date kStartTime;

    @Column(name = "k_end_time")
    private Date kEndTime;

    /**
     * 是否有效
     */
    @Column(name = "k_is_active")
    private Integer kIsActive;
}