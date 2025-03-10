package com.junsu.cyr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CYRApplication {
    public static void main(String[] args) {
        SpringApplication.run(CYRApplication.class, args);
    }
}
