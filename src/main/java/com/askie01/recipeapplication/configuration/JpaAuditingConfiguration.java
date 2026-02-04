package com.askie01.recipeapplication.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {

    @Bean
    @Primary
    @ConditionalOnProperty(
            name = "component.auditor-type",
            havingValue = "recipe-service-auditor",
            matchIfMissing = true
    )
    public AuditorAware<String> recipeServiceAuditor() {
        return () -> Optional.of("RECIPE_SERVICE");
    }
}
