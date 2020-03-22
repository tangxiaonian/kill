package com.tang.kill.mapper;

import com.tang.kill.domain.TbKillSuccess;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbKillSuccessMapper extends Mapper<TbKillSuccess> {


    public List<com.tang.kill.dto.TbKillSuccess> getKillSuccessOrderList();

}