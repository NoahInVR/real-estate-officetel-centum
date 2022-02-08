package com.busancentum.realestatenoah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RealestatenoahApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealestatenoahApplication.class, args);
    }

}
