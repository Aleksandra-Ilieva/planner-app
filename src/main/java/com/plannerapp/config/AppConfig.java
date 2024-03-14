package com.plannerapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.ui.Model;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper mapper(){
        return  new ModelMapper();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new Pbkdf2PasswordEncoder();
    }
}
