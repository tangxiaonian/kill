package com.tang.kill.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname RabbitConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/3/21 8:44
 * @Created by ASUS
 */
@Configuration
public class RabbitConfig {


    /**
     * email 消息 direct 方式
     */
    private final static String MQ_MESSAGE_EMAIL_QUEUE = "mq.message.email.queue";
    private final static String MQ_MESSAGE_EMAIL_EXCHANGE = "mq.message.email.exchange";
    private final static String MQ_MESSAGE_EMAIL_ROUTINGKEY = "message.email";

    /**
     * 正常消息队列 topic 方式
     */
    private final static String MQ_MESSAGE_QUEUE = "mq.message.queue";
    private final static String MQ_MESSAGE_EXCHANGE = "mq.message.exchange";
    private final static String MQ_MESSAGE_ROUTINGKEY = "mq.*";

    /**
     * 定义死信交换机
     */
    private final static String MQ_MESSAGE_DEAD_EXCHANGE = "mq.message.dead.exchange";

    private final static String MQ_MESSAGE_DEAD_ROUTINGKEY = "mq.dead.message";

    private final static Integer MQ_MESSAGE_DEAD_TTL = 10000;

    /**
     * 绑定到死信交换机的队列
     */
    private final static String MQ_MESSAGE_DEAD_REAL_QUEUE = "mq.message.dead.real.queue";

    /**
     * 死信队列 交换机标识符
     */
    private static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";

    /**
     * 死信队列交换机绑定键标识符
     */
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /**
     * 死信队列消息驻留时间标识符
     */
    private final static String MQ_MESSAGE_TTL = "x-message-ttl";

    //==========================================基本队列--->死信交换机
    /**
     * 定义死信队列 绑定 死信交换机,路由键
     * @return
     */
    @Bean
    public Queue messageTopicQueue() {

        Map<String, Object> map = new HashMap<>(16);

        map.put(DEAD_LETTER_QUEUE_KEY, MQ_MESSAGE_DEAD_EXCHANGE);

        map.put(DEAD_LETTER_ROUTING_KEY, MQ_MESSAGE_DEAD_ROUTINGKEY);

//       消息在队列里面存活的最大时间   超出入死信队列
        map.put(MQ_MESSAGE_TTL, MQ_MESSAGE_DEAD_TTL);

        return new Queue(MQ_MESSAGE_QUEUE, true, false, false, map);
    }

    /**
     * 基本交换机
     * @return
     */
    @Bean
    public TopicExchange messageTopicExchange() {
        return new TopicExchange(MQ_MESSAGE_EXCHANGE, true, false);
    }

    /**
     * 基本绑定关系 = 死信队列 + 基本交换机
     * @param messageTopicQueue
     * @param messageTopicExchange
     * @return
     */
    @Bean
    public Binding bindMessageExchange(@Qualifier("messageTopicQueue") Queue messageTopicQueue,
                                       @Qualifier("messageTopicExchange") TopicExchange messageTopicExchange) {
        return BindingBuilder.bind(messageTopicQueue).to(messageTopicExchange).with(MQ_MESSAGE_ROUTINGKEY);
    }
    //===========================================死信交换机---->基本队列
    /**
     * 定义消费者队列 绑定死信交换机 消费交换机的内容
     * @return
     */
    @Bean
    public Queue messageDeadRealQueue() {
        return new Queue(MQ_MESSAGE_DEAD_REAL_QUEUE, true);
    }

    /**
     * 死信交换机
     * @return
     */
    @Bean
    public DirectExchange messageDeadDirectExchange() {
        return new DirectExchange(MQ_MESSAGE_DEAD_EXCHANGE, true, false);
    }

    /**
     * 消费者队列绑定死信交换机
     * @param messageDeadRealQueue
     * @param messageDeadDirectExchange
     * @return
     */
    @Bean
    public Binding bindDeadMessageExchange(@Qualifier("messageDeadRealQueue") Queue messageDeadRealQueue,
                                           @Qualifier("messageDeadDirectExchange") DirectExchange messageDeadDirectExchange) {
        return BindingBuilder.bind(messageDeadRealQueue).to(messageDeadDirectExchange).with(MQ_MESSAGE_DEAD_ROUTINGKEY);
    }

    //********************EMAIL*********************

    @Bean
    public Queue messageEmailDirectQueue() {
        return new Queue(MQ_MESSAGE_EMAIL_QUEUE, true, false,false);
    }

    @Bean
    public DirectExchange messageEmailDirectExchange() {
        return new DirectExchange(MQ_MESSAGE_EMAIL_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindMessageEmailExchange(@Qualifier("messageEmailDirectQueue") Queue messageEmailDirectQueue,
                                            @Qualifier("messageEmailDirectExchange") DirectExchange messageEmailDirectExchange) {
        return BindingBuilder.bind(messageEmailDirectQueue).to(messageEmailDirectExchange).with(MQ_MESSAGE_EMAIL_ROUTINGKEY);
    }

    //********************EMAIL***********************

}