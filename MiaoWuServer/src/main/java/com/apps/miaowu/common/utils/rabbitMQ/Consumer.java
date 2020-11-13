package com.apps.miaowu.common.utils.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author lzyw
 * @create 2020/7/21 13:43
 */
@Component
public class Consumer {
    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("rabbitmq_queue"))
    public void process(String message) {
        System.out.println("消费者消费信息====" + message);
    }
}
