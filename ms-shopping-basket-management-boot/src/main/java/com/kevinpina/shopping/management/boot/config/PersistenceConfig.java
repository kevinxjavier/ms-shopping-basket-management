package com.kevinpina.shopping.management.boot.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kevinpina.shopping.management.infrastructure.audit.AuditorAwareImpl;

@Configuration
@EnableTransactionManagement
@EntityScan("com.kevinpina.shopping.management.infrastructure.repository.db")
@EnableJpaRepositories(basePackages = "com.kevinpina.shopping.management.infrastructure.repository.db.repository")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {
    
    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
    
}
