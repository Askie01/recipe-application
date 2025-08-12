package com.askie01.recipeapplication.auditor;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class ServiceAuditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("RECIPE_SERVICE");
    }
}
