package com.bluehair.hanghaefinalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HanghaeFinalProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(HanghaeFinalProjectApplication.class, args);
    }
}
