package com.tang.kill.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @Classname TbKillShop
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/21 10:19
 * @Created by ASUS
 */
@Table(name = "tb_kill_shop")
@Data
public class TbKillShop extends com.tang.kill.domain.TbShop {

    @Column(name = "is_can_kill")
    private Integer isCanKill;

}