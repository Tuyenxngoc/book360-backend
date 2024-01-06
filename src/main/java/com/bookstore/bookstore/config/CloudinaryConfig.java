package com.bookstore.bookstore.config;

import com.cloudinary.Cloudinary;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
@PropertySource("classpath:config.properties")
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryConfig {

    private String cloudName;

    private String apiKey;

    private String apiSecret;

    @Bean
    public Cloudinary cloudConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }

}
