package io.project.producer.app.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@ComponentScan("io.project")
@EnableAsync
@Slf4j
public class ProducerApplication {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(ProducerApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args).getEnvironment();

    }

    @Bean("threadPoolTaskExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(100);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Kafka-stream-");
        return executor;
    }

//    @Bean
//    public ApplicationRunner runner(RoutingKafkaTemplate routingTemplate) {
//        return args -> {
//            routingTemplate.send("event", "new event happened");
//            routingTemplate.send("order", "new order happened");
//        };
//    }
    @Autowired
    private RoutingKafkaTemplate routingTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        for (int i = 0; i < 1000000; i++) {
            long currentTimeMillis = System.currentTimeMillis();
            routingTemplate.send("event", "@event " + currentTimeMillis);
            routingTemplate.send("order", "#order " + currentTimeMillis);
            routingTemplate.send("syslog", "#syslog " + currentTimeMillis);
        }

    }

    //https://docs.spring.io/spring-kafka/docs/current/reference/html/#routing-template
}
