package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override public void addCorsMappings(CorsRegistry reg){
        reg.addMapping("/**")
                .allowedOrigins("http://localhost:19006","http://localhost:3000","exp://127.0.0.1:*")
                .allowedMethods("GET","POST","PUT","DELETE","PATCH","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
