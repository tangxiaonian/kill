package com.tang.kill.scheduled;

import com.tang.kill.domain.TbKillSuccess;
import com.tang.kill.service.TbKillShopSuccessServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname ScheduledService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/21 21:35
 * @Created by ASUS
 */
//@Component
public class ScheduledService {

    @Resource
    private TbKillShopSuccessServiceImpl tbKillShopSuccessServiceImpl;

    /**
     * 订单存活时间
     */
    private static final Integer TTL = 30;

    /**
     * 利用定时任务 让秒杀成功的订单失效
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void checkKillSuccessOrderIsExpired() {

        System.out.println( Thread.currentThread().getName() + "开始执行了....." );

        List<com.tang.kill.dto.TbKillSuccess> killSuccessOrderList = tbKillShopSuccessServiceImpl.getKillSuccessOrderList();

        if (killSuccessOrderList != null && killSuccessOrderList.size() > 0) {

            killSuccessOrderList.forEach((item) -> {

                if (item.getDiffTime() > TTL) {
                    System.out.println( "开始定时更新秒杀记录状态...." );
                    tbKillShopSuccessServiceImpl.updateStatus(item.getId(),-1);

                }

            });

        }


    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void checkKillSuccessOrderIsExpiredV1() {
        System.out.println( Thread.currentThread().getName() + "开始执行了....." );
    }

    @Scheduled(cron = "0/8 * * * * ?")
    public void checkKillSuccessOrderIsExpiredV2() {
        System.out.println( Thread.currentThread().getName() + "开始执行了....." );
    }

}