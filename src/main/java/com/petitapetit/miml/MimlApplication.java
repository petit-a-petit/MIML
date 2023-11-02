package com.petitapetit.miml;

import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class MimlApplication {

    public static void main(String[] args) {
        SpringApplication.run(MimlApplication.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Notification-");
        executor.initialize();

        executor.setTaskDecorator(new TaskDecorator() {
            @Override
            public Runnable decorate(Runnable runnable) {
                return () -> {
                    String threadName = Thread.currentThread().getName();
                    long start = System.currentTimeMillis();
                    log.info("Starting async task in thread : {}", threadName);
                    runnable.run();
                    long end = System.currentTimeMillis();
                    log.info("It took "+(end-start)+"ms to process the async task.");
                };
            }
        });

        return executor;
    }
}
