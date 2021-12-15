package io.project.consumer.app.senders;

import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventListener {

    
    @KafkaListener(topics = {"event"})
    public void order(final @Payload String message
    ) {
        log.info("Received From Topic order: " + message);

    }


}
