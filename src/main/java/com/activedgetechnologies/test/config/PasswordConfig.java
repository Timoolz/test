package com.activedgetechnologies.test.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class PasswordConfig {


    @Value("${password.encoder.strength}")
    private int strength;

    @Bean(name = "passwordEncoder")
    public BCryptPasswordEncoder myPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(strength, new SecureRandom());

        return bCryptPasswordEncoder;
    }
}
