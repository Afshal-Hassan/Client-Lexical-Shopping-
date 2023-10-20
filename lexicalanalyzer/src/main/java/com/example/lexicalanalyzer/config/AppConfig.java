package com.example.lexicalanalyzer.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class AppConfig {


    @Bean
    public File file() {
        return new File("C:/Client/lexicalanalyzer/src/main/java/com/example/lexicalanalyzer/lexical/Test.txt");
    }
}
