package com.travix.medusa.busyflights.integrated;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestDataContext {
    @Bean(name="wireMockBean", initMethod = "start", destroyMethod = "stop")
    @Primary
    public WireMockServer wireMockBean() {
        return new WireMockServer(WireMockConfiguration.wireMockConfig()
                .dynamicPort()
                .usingFilesUnderClasspath("wiremock"));
    }

}
