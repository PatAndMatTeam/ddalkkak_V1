package com.ddalkkak.splitting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class SplittingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplittingApplication.class, args);
    }

}
