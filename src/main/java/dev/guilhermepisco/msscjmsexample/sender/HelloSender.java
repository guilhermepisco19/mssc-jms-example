package dev.guilhermepisco.msscjmsexample.sender;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.guilhermepisco.msscjmsexample.config.JmsConfig;
import dev.guilhermepisco.msscjmsexample.module.HelloWorldMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
      log.info("Sending message");

        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        log.info("Message sent");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SND_RCV_QUEUE, session -> {

            try {
                HelloWorldMessage message = HelloWorldMessage.builder()
                        .id(UUID.randomUUID())
                        .message("Hello World!")
                        .build();

                Message helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                helloMessage.setStringProperty("_type", "dev.guilhermepisco.msscjmsexample.module.HelloWorldMessage");
                return helloMessage;
            } catch (JsonProcessingException e) {
                throw new JMSException(e.getMessage());
            }

        });

        if(receivedMsg == null){
            throw new JMSException("Received a null message!!!!");
        }

        log.info("Message received: {}", receivedMsg.getBody(String.class));
    }
}
