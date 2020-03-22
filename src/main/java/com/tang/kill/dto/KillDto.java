package com.tang.kill.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname KillDto
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/20 23:05
 * @Created by ASUS
 */
@AllArgsConstructor
@Data
public class KillDto implements Serializable {

    private Integer uId;

    private Integer kId;
}