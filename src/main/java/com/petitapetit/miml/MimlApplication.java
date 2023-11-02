package com.petitapetit.miml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MimlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MimlApplication.class, args);
    }
}
