package com.tang.kill.rabbitmq;

import com.tang.kill.common.MapperUtils;
import com.tang.kill.domain.TbKillSuccess;
import com.tang.kill.service.TbKillShopServiceImpl;
import com.tang.kill.service.TbKillShopSuccessServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Optional;

/**
 * @Classname MessageConsumerListener
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/21 9:21
 * @Created by ASUS
 */
@Slf4j
@Component
public class MessageConsumerListener {

    @Resource
    private TbKillShopSuccessServiceImpl tbKillShopSuccessServiceImpl;

    private final static String MQ_MESSAGE_EMAIL_QUEUE = "mq.message.email.queue";

    /**
     * 消费死信队列里面数据的队列
     */
    private final static String MQ_MESSAGE_DEAD_REAL_QUEUE = "mq.message.dead.real.queue";

    @RabbitListener(queues = {MQ_MESSAGE_EMAIL_QUEUE})
    public void receiveMessage(@Payload Integer uId) {

        log.info("接收用户ID....并发送邮件消息进行通知..." + uId);

    }

    /**
     * 查询订单状态，查看用户是否支付
     * @param orderId
     */
    @RabbitListener(queues = {MQ_MESSAGE_DEAD_REAL_QUEUE})
    public void receiveDeadMessage(@Payload String orderId) {

        log.info("查看订单是否超时....");

        if (!StringUtils.isEmpty(orderId)) {

            Optional<TbKillSuccess> optionalTbKillSuccess = Optional.ofNullable(
                    tbKillShopSuccessServiceImpl.selectByPrimaryKey(orderId));

           optionalTbKillSuccess.ifPresent(tbKillSuccess -> {

               // 指定时间内没有付款
               if (tbKillSuccess.getStatus() == 0) {

                   // 更新订单状态无效
                   tbKillShopSuccessServiceImpl.updateStatus(tbKillSuccess.getId(),-1);

                   log.info("用户没有付款订单设置无效.....");

               }

           });

        }


    }


}