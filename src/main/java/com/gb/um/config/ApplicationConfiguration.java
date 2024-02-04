package com.gb.um.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Application Level configurations
 */
@Configuration
@Slf4j
public class ApplicationConfiguration {
    /**
     * Encoder to encode the password before store in db
     * @return BCryptPasswordEncoder to encode and decode passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
