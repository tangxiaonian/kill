package com.tang.kill.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ASUS
 */
@Data
@Table(name = "tb_kill_success")
public class TbKillSuccess implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "u_id")
    private Integer uId;

    @Column(name = "k_id")
    private Integer kId;

    @Column(name = "phone")
    private String phone;

    @Column(name = "`status`")
    private Integer status;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH;MM:ss")
    private Date createTime;
}