package com.example.mytttptestpro.MQ;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "Canal-Producer", topic = "XYH-example",
        selectorType = SelectorType.TAG)
public class CanalMqConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {

    }
}
