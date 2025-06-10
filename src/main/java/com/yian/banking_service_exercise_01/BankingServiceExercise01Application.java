package com.yian.banking_service_exercise_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BankingServiceExercise01Application {

    public static void main(String[] args) {
        SpringApplication.run(BankingServiceExercise01Application.class, args);
    }

}
