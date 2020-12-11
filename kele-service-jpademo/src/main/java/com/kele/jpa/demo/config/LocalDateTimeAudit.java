package com.kele.jpa.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
public class LocalDateTimeAudit implements AuditorAware<LocalDateTime> {
    @Override
    public Optional<LocalDateTime> getCurrentAuditor() {
        return Optional.of(LocalDateTime.now());
    }
}
