package com.askie01.recipeapplication.auditor;

import java.util.Optional;

public class RecipeServiceStringAuditor implements StringAuditor {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("RECIPE_SERVICE");
    }
}
