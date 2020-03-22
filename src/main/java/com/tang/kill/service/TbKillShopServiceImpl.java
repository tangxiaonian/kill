package com.tang.kill.service;

import com.tang.kill.config.SnowflakeDistributeId;
import com.tang.kill.domain.TbKillSuccess;
import com.tang.kill.dto.KillDto;
import com.tang.kill.dto.TbKillShop;
import com.tang.kill.mapper.TbKillShopMapper;
import com.tang.kill.rabbitmq.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Classname TbKillShopServiceImpl
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/20 22:50
 * @Created by ASUS
 */
@Slf4j
@Service
public class TbKillShopServiceImpl {

    @Resource
    private TbKillShopMapper tbKillShopMapper;

    @Resource
    private TbKillShopSuccessServiceImpl tbKillShopSuccessServiceImpl;

    @Resource
    private SnowflakeDistributeId snowflakeDistributeId;

    @Resource
    private SenderService senderServiceImpl;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取可以被秒杀的商品
     * @return
     */
    public List<TbKillShop> getKillShop() {

        return tbKillShopMapper.getKillShop();
    }

    /**
     * 商品秒杀
     * @param killDto
     * @return
     */
    public Boolean killShop(KillDto killDto) {

        // 该用户已经抢购成功过了
        if (tbKillShopSuccessServiceImpl.killSuccessOrderIsExistByUidKid(killDto.getUId(),killDto.getKId()) > 0) {
            return false;
        }
        // 1.更新库存
        if (this.updateKillShopCount(killDto)) {
            //2.插入秒杀成功用户订单记录
            return this.insertKillSuccessRecord(killDto);

        }

        return false;
    }

    /**
     * 商品秒杀： Redis 分布式锁
     * @param killDto
     * @return
     */
    public Boolean killShopV1(KillDto killDto) {

        // 该用户已经抢购成功过了
        if (tbKillShopSuccessServiceImpl.killSuccessOrderIsExistByUidKid(killDto.getUId(),killDto.getKId()) > 0) {
            return false;
        }

        String key = killDto.getUId() + ":" + killDto.getKId() + "redisLock";

        try {

            // 加锁
            if (stringRedisTemplate.opsForValue().setIfAbsent(key, Thread.currentThread().getName(),
                    5,TimeUnit.SECONDS)) {

                // 1.更新库存
                if (this.updateKillShopCount(killDto)) {
                    //2.插入秒杀成功用户订单记录
                    return this.insertKillSuccessRecord(killDto);

                }

            }

        }finally {

            // 释放锁
            if (Thread.currentThread().getName().equals(stringRedisTemplate.opsForValue().get(key))) {

                stringRedisTemplate.delete(key);

            }

        }


        return false;
    }


    /**
     * 更新库存
     * @param killDto
     * @return
     */
    private Boolean updateKillShopCount(KillDto killDto) {

        boolean result = false;

        // 用户没有抢购记录，进行尝试抢购
        com.tang.kill.domain.TbKillShop tbKillShop = tbKillShopMapper.selectByPrimaryKey(killDto.getKId());

        // 要秒杀的记录存在  &&  数量存在
        if (tbKillShop != null && tbKillShop.getCounts() > 0) {

            // 1.减少库存
            int updateResult = tbKillShopMapper.updateCounts(tbKillShop.getKId());

            if (updateResult > 0) {
                result = true;
            }

        }

        return result;
    }

    /**
     * 插入秒杀成功的记录
     * @param killDto
     * @return
     */
    private Boolean insertKillSuccessRecord(KillDto killDto) {

        boolean result = false;

        // 插入一条 秒杀成功的记录

        TbKillSuccess tbKillSuccess = new TbKillSuccess();

        tbKillSuccess.setId(snowflakeDistributeId.nextId() + "");
        tbKillSuccess.setUId(killDto.getUId());
        tbKillSuccess.setKId(killDto.getKId());
        tbKillSuccess.setPhone("18855092612");
        tbKillSuccess.setCreateTime(new Date());
        // 0 没付款
        tbKillSuccess.setStatus(0);

        // 双重检查  再次检查用户有没有购买过
        if (tbKillShopSuccessServiceImpl.killSuccessOrderIsExistByUidKid(killDto.getUId(),killDto.getKId()) > 0) {
            return false;
        }

        // 2.保存秒杀抢购成功的订单记录
        int killSuccessRecord = tbKillShopSuccessServiceImpl.saveTbKillSuccessRecord(tbKillSuccess);

        if (killSuccessRecord > 0) {

            // 邮件通知用户秒杀成功
            senderServiceImpl.emailNoticeUserKillSuccess(killDto.getUId());

            // 消息发送到死信队列解决订单超时
            senderServiceImpl.sendKillSuccessOrderExpireMsg(tbKillSuccess.getId());

            result = true;
        }

        return result;

    }


}