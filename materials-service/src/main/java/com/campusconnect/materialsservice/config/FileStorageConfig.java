package com.campusconnect.materialsservice.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class FileStorageConfig {

    /**
     * If you need to set max‐upload sizes programmatically instead of application.yml:
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 10 MB max file size
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        // 10 MB max request size
        factory.setMaxRequestSize(DataSize.ofMegabytes(10));
        return factory.createMultipartConfig();
    }
}
