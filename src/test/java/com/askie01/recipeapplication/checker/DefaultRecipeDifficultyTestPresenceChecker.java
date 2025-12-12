package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.entity.Recipe;

public class DefaultRecipeDifficultyTestPresenceChecker implements RecipeDifficultyTestPresenceChecker {

    @Override
    public boolean hasDifficulty(Recipe recipe) {
        return hasNotNullDifficulty(recipe);
    }

    private boolean hasNotNullDifficulty(Recipe recipe) {
        return recipe.getDifficulty() != null;
    }
}
