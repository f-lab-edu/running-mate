package com.runningmate.runningmate.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig{

    @Value("${jasypt.encryptor.algorithm}")
    private String encryptAlgorithm;

    @Value("${jasypt.encryptor.password}")
    private String encryptKey;

    @Bean("jasyptStringEncryptor")
    public StringEncryptor  stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encryptKey);
        config.setAlgorithm(encryptAlgorithm);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize(1);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}
