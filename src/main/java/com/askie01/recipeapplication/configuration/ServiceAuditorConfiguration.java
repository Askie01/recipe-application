package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.auditor.ServiceAuditor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
@ConditionalOnProperty(name = "component.auditor.type", havingValue = "service")
public class ServiceAuditorConfiguration {

    @Bean
    public AuditorAware<String> serviceAuditor() {
        return new ServiceAuditor();
    }
}
