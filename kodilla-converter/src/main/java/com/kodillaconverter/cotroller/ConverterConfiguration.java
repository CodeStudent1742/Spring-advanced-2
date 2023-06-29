package com.kodillaconverter.cotroller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ConverterConfiguration implements WebMvcConfigurer {

    @Bean
    public MyCustomCSVConverter customCSVConverter() {
        System.out.println("Creating MyCustomCSVConverter bean");
        return new MyCustomCSVConverter();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        System.out.println("Configuring message converters");
        converters.clear();
        converters.add(customCSVConverter());
    }
}