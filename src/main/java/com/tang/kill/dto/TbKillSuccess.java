package com.tang.kill.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author ASUS
 */
@Data
@Table(name = "tb_kill_success")
public class TbKillSuccess extends com.tang.kill.domain.TbKillSuccess implements Serializable {

    @Column(name = "diffTime")
    private Integer diffTime;
}