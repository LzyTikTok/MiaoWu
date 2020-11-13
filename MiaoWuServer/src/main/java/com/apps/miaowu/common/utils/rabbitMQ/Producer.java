package com.apps.miaowu.common.utils.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author lzy
 * @create 2020/7/21 13:40
 */
@Component
public class Producer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void produce(){
        String message = new Date() + "Beijing";
        System.out.println("生产者生产信息=====" + message);
        rabbitTemplate.convertAndSend("rabbitmq_queue", message);
    }
}
