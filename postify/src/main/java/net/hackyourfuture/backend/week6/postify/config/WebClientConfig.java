package net.hackyourfuture.backend.week6.postify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WebClientConfig {

    @Bean
    public RestClient lyricsRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.lyrics.ovh/v1")
                .build();
    }
}