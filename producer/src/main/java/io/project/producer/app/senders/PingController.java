package io.project.producer.app.senders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lilith
 */
@RestController
@RequestMapping("/run")
public class PingController {
    
    @Autowired
    KafkaRegularSender kafkaRegularSender;
    
    @GetMapping("/send/message")
    public String sendMessage(){
        kafkaRegularSender.sendMessage("event", "This is my event "+System.currentTimeMillis());
        return "send";
    }
}
