package com.example.gdsc_2nd_w1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Gdsc2ndW1Application {

    public static void main(String[] args) {
        SpringApplication.run(Gdsc2ndW1Application.class, args);
    }

    @Bean
    public Encryptor encryptConfig() {
        return new BCryptEncryptor();
    }


}
