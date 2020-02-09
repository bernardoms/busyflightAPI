package com.travix.medusa.busyflights.config;

        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.boot.web.client.RestTemplateBuilder;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Value("${restTemplate.connectionTimout}")
    private int restTemplateTimeout;
    @Value("${restTemplate.readTimeout}")
    private int readTimeout;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(restTemplateTimeout)
                .setReadTimeout(readTimeout)
                .build();
    }
}
