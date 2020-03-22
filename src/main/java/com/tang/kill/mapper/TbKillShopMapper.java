package com.tang.kill.mapper;

import com.tang.kill.domain.TbKillShop;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author ASUS
 */
public interface TbKillShopMapper extends Mapper<TbKillShop> {

    /**
     * 获取可以被秒杀的商品
     * @return
     */
    List<com.tang.kill.dto.TbKillShop> getKillShop();

    /**
     * 数据库层面上优化: 保证count > 0
     * @param kId
     * @return
     */
    @Update("update tb_kill_shop tks set counts = counts - 1 where k_id = #{kId} and tks.counts > 0")
    int updateCounts(@Param("kId") Integer kId);

}