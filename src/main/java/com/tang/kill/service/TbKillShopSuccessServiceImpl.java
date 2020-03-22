package com.tang.kill.service;

import com.tang.kill.domain.TbKillSuccess;
import com.tang.kill.mapper.TbKillSuccessMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname TbKillShopServiceImpl
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/20 22:50
 * @Created by ASUS
 */
@Service
public class TbKillShopSuccessServiceImpl {

    @Resource
    private TbKillSuccessMapper tbKillSuccessMapper;

    /**
     * 用户秒杀记录是否存在，防止重复秒杀
     * @param uId
     * @param kId
     * @return
     */
    int killSuccessOrderIsExistByUidKid(Integer uId, Integer kId) {

        Example example = new Example(TbKillSuccess.class);

        example.createCriteria()
                .andEqualTo("uId", uId)
                .andEqualTo("kId",kId);

        return tbKillSuccessMapper.selectCountByExample(example);

    }

    public List<com.tang.kill.dto.TbKillSuccess> getKillSuccessOrderList(){

        return tbKillSuccessMapper.getKillSuccessOrderList();

    }

    int saveTbKillSuccessRecord(TbKillSuccess tbKillSuccess) {

        return tbKillSuccessMapper.insert(tbKillSuccess);

    }

    public TbKillSuccess selectByPrimaryKey(String orderId) {

        return tbKillSuccessMapper.selectByPrimaryKey(orderId);
    }

    public void updateStatus(String id, int status) {

        TbKillSuccess tbKillSuccess = new TbKillSuccess();

        tbKillSuccess.setId( id );
        tbKillSuccess.setStatus(status);

        tbKillSuccessMapper.updateByPrimaryKeySelective(tbKillSuccess);

    }
}