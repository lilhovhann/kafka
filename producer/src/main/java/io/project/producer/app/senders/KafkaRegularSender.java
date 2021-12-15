package io.project.producer.app.senders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * The Kafka sender
 *
 * @author armena
 */
@Service
@Component
@Slf4j
public class KafkaRegularSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaRegularSender(KafkaTemplate<String, String> kafkaTemplate) {
        super();
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {

        log.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send("event", message);
    }

}
