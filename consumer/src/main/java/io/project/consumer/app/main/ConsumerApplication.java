package io.project.consumer.app.main;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("io.project")
@EnableAsync
public class ConsumerApplication {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(ConsumerApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args).getEnvironment();

    }
}
