package com.petitapetit.miml;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableBatchProcessing
public class MimlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MimlApplication.class, args);
    }
}

