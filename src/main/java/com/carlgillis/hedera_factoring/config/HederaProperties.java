package com.carlgillis.hedera_factoring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "hedera")
public class HederaProperties {
    private String network;
    private String operatorId;
    private String operatorKey;
}
