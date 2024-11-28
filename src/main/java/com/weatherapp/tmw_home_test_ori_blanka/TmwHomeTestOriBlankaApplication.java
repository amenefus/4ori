package com.weatherapp.tmw_home_test_ori_blanka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TmwHomeTestOriBlankaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TmwHomeTestOriBlankaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("SPRING_DATASOURCE_URL: " + System.getenv("SPRING_DATASOURCE_URL"));
        System.out.println("SPRING_DATASOURCE_USERNAME: " + System.getenv("SPRING_DATASOURCE_USERNAME"));
        System.out.println("SPRING_DATASOURCE_PASSWORD: " + System.getenv("SPRING_DATASOURCE_PASSWORD"));
    }
}