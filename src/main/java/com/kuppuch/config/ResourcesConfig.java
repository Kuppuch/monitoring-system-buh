package com.kuppuch.config;

import com.kuppuch.interseptor.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ResourcesConfig implements WebMvcConfigurer {

    @Value("${filepath}")
    private String resourcePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/content/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/content/**")
                .addResourceLocations("file:///" + resourcePath + "/");
    }
}
