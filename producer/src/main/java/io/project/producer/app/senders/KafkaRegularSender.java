package io.project.producer.app.senders;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * The Kafka sender
 *
 * @author armena
 */
@Service
@Component
@Slf4j
public class KafkaRegularSender {

    /**
     * The Kafka template of input
     */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Creates new instance of Kafka sender
     *
     * @param kafkaTemplate The Kafka template
     */
    @Autowired
    public KafkaRegularSender(KafkaTemplate<String, String> kafkaTemplate) {
        super();
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async("threadPoolTaskExecutor")
    public ListenableFuture<SendResult<String, String>> sendMessage(String topic, String message) {
        
        log.info("Start SEND Sending to  channel " + topic);
        int nThreads = Runtime.getRuntime().availableProcessors();
        log.info("AVAILABLE nThreads " + nThreads);

        ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topic, message);
        // register a callback with the listener to receive the result of the send asynchronously
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("@KAFKA Success sent message='{}' with offset={}", message,
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(@NotNull Throwable ex) {
                log.error("@KAFKA FAIL: unable to send message='{}'", message, ex);
            }
        });
        return future;
    }

}
