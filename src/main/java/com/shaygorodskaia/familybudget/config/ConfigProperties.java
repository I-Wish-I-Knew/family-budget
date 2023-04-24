package com.shaygorodskaia.familybudget.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    private final Environment environment;

    public String getSecretKey() {
        return environment.getProperty("secret-key");
    }

}
