//package com.bikash.person.configs;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//@Configuration
//public class JavaMailSenderConfig {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.mail")
//
//    public JavaMailSender javaMailSender() {
//        return new JavaMailSenderImpl();
//    }
//}
