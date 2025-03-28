package com.junsu.cyr;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class CYRApplication {
    public static void main(String[] args) {
        SpringApplication.run(CYRApplication.class, args);

        LocalDateTime now = LocalDateTime.now();
        System.out.println("서버 시작 : "+now);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
