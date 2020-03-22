package com.tang.kill.rabbitmq;

import com.tang.kill.domain.TbKillSuccess;
import com.tang.kill.service.TbKillShopSuccessServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Classname SenderService
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/21 17:18
 * @Created by ASUS
 */
@Slf4j
@Component
public class SenderService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private TbKillShopSuccessServiceImpl tbKillShopSuccessServiceImpl;

    /**
     * 基本交换机
     */
    private final static String MQ_MESSAGE_EXCHANGE = "mq.message.exchange";

    /**
     * 邮件交换机
     */
    private final static String MQ_MESSAGE_EMAIL_EXCHANGE = "mq.message.email.exchange";

    private final static String MQ_MESSAGE_EMAIL_ROUTINGKEY = "message.email";

    /**
     * 邮件发送
     * @param uId
     */
    public void emailNoticeUserKillSuccess(Integer uId) {

        rabbitTemplate.setMessageConverter(new SimpleMessageConverter());

        rabbitTemplate.convertAndSend(MQ_MESSAGE_EMAIL_EXCHANGE, MQ_MESSAGE_EMAIL_ROUTINGKEY, uId);
    }

    /**
     * 定时审查订单
     * @param orderId
     */
    public void sendKillSuccessOrderExpireMsg(String orderId) {

        // 根据 秒杀抢购成功的订单号 查询这条记录
        TbKillSuccess tbKillSuccess = tbKillShopSuccessServiceImpl.selectByPrimaryKey(orderId);

        // 秒杀成功的用户记录存在
        if (tbKillSuccess != null) {

            log.info(" 开始审查超时订单....... ");

//            rabbitTemplate.setExchange(MQ_MESSAGE_EXCHANGE);

//            rabbitTemplate.setRoutingKey("mq.info");

//            rabbitTemplate.convertAndSend(tbKillSuccess, message -> {

//                可以动态设置 TTL

//                message.getMessageProperties().setExpiration(5000L);
//
//                return message;

//            });

            rabbitTemplate.convertAndSend(MQ_MESSAGE_EXCHANGE, "mq.info", orderId);

        }

    }

}