package com.wccgroup.distancecalculator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DbProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
