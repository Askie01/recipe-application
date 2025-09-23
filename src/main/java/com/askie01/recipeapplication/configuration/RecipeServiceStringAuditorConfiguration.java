package com.askie01.recipeapplication.configuration;

import com.askie01.recipeapplication.auditor.RecipeServiceStringAuditor;
import com.askie01.recipeapplication.auditor.StringAuditor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "component.auditor-type", havingValue = "recipe-service-string-auditor")
public class RecipeServiceStringAuditorConfiguration {

    @Bean
    public StringAuditor recipeServiceStringAuditor() {
        return new RecipeServiceStringAuditor();
    }
}
