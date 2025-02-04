package com.nwic.gt.decoder.file.tracking.system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private Environment env;
//
//    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////
////        registry.addMapping("/**")
////                .allowedOrigins("http://localhost:4200/","http://localhost:3000/","http://localhost:5000",)
////                .allowCredentials(true)
////                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
////                .allowedHeaders("*");
////                registry
////                        .addMapping(env.getProperty("logging.file.name")).allowedOrigins("http://localhost:9096");
////    }
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")  // Allow all origins
//                .allowCredentials(false)  // You can set it to false if you don't need credentials
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*");
//    }
//
//
//}
