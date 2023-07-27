package dev.guilhermepisco.msscjmsexample.listener;

import dev.guilhermepisco.msscjmsexample.config.JmsConfig;
import dev.guilhermepisco.msscjmsexample.module.HelloWorldMessage;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloMessageListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message){
        log.info("I got a message!");

        log.info("Message received: {}", helloWorldMessage);
    }
}
