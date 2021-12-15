package io.project.consumer.app.senders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventListener {

    @RetryableTopic(attempts = "5", backoff = @Backoff(delay = 1000, multiplier = 2.0))
    @KafkaListener(id = "event", topics = "event")
    public void event(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic
    ) {
        log.info("Received From Topic=> " + topic + " " + message);

    }

    @DltHandler
    public void dlt(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.error(in + " from " + topic);
    }

    @KafkaListener(topics = {"order"})
    public void order(final @Payload String message
    ) {
        log.info("Received From Topic order: " + message);

    }

    @KafkaListener(topics = {"syslog"})
    public void syslog(final @Payload String message
    ) {
        log.info("Received From Topic syslog: " + message);

    }
}
