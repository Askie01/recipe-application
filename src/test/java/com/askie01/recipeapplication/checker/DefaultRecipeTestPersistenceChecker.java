package com.askie01.recipeapplication.checker;

import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.Recipe;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class DefaultRecipeTestPersistenceChecker implements RecipeTestPersistenceChecker {

    private final LongIdTestPresenceChecker longIdTestPresenceChecker;
    private final ImageTestPresenceChecker imageTestPresenceChecker;
    private final StringNameTestPresenceChecker stringNameTestPresenceChecker;
    private final DescriptionTestPresenceChecker descriptionTestPresenceChecker;
    private final RecipeDifficultyTestPresenceChecker recipeDifficultyTestPresenceChecker;
    private final CategoryTestPersistenceChecker categoryTestPersistenceChecker;
    private final IngredientTestPersistenceChecker ingredientTestPersistenceChecker;
    private final ServingsTestPresenceChecker servingsTestPresenceChecker;
    private final CookingTimeTestPresenceChecker cookingTimeTestPresenceChecker;
    private final InstructionsTestPresenceChecker instructionsTestPresenceChecker;
    private final SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker;
    private final LongVersionTestPresenceChecker longVersionTestPresenceChecker;

    @Override
    public boolean wasCreated(Recipe recipe) {
        final boolean hasId = hasId(recipe);
        final boolean hasImage = hasImage(recipe);
        final boolean hasName = hasName(recipe);
        final boolean hasDescription = hasDescription(recipe);
        final boolean hasDifficulty = hasDifficulty(recipe);
        final boolean hasCategories = hasCategories(recipe);
        final boolean hasIngredients = hasIngredients(recipe);
        final boolean hasServings = hasServings(recipe);
        final boolean hasCookingTime = hasCookingTime(recipe);
        final boolean hasInstructions = hasInstructions(recipe);
        final boolean hasCreatedAt = hasCreatedAt(recipe);
        final boolean hasCreatedBy = hasCreatedBy(recipe);
        final boolean doesNotHaveUpdatedAt = doesNotHaveUpdatedAt(recipe);
        final boolean doesNotHaveUpdatedBy = doesNotHaveUpdatedBy(recipe);
        final boolean hasVersion = hasVersion(recipe);
        return hasId &&
                hasImage &&
                hasName &&
                hasDescription &&
                hasDifficulty &&
                hasCategories &&
                hasIngredients &&
                hasServings &&
                hasCookingTime &&
                hasInstructions &&
                hasCreatedAt &&
                hasCreatedBy &&
                doesNotHaveUpdatedAt &&
                doesNotHaveUpdatedBy &&
                hasVersion;
    }

    @Override
    public boolean wasUpdated(Recipe recipe) {
        final boolean hasId = hasId(recipe);
        final boolean hasImage = hasImage(recipe);
        final boolean hasName = hasName(recipe);
        final boolean hasDescription = hasDescription(recipe);
        final boolean hasDifficulty = hasDifficulty(recipe);
        final boolean hasCategories = hasCategories(recipe);
        final boolean hasIngredients = hasIngredients(recipe);
        final boolean hasServings = hasServings(recipe);
        final boolean hasCookingTime = hasCookingTime(recipe);
        final boolean hasInstructions = hasInstructions(recipe);
        final boolean hasCreatedAt = hasCreatedAt(recipe);
        final boolean hasCreatedBy = hasCreatedBy(recipe);
        final boolean hasUpdatedAt = hasUpdatedAt(recipe);
        final boolean hasUpdatedBy = hasUpdatedBy(recipe);
        final boolean hasVersion = hasVersion(recipe);
        return hasId &&
                hasImage &&
                hasName &&
                hasDescription &&
                hasDifficulty &&
                hasCategories &&
                hasIngredients &&
                hasServings &&
                hasCookingTime &&
                hasInstructions &&
                hasCreatedAt &&
                hasCreatedBy &&
                hasUpdatedAt &&
                hasUpdatedBy &&
                hasVersion;
    }

    private boolean hasId(Recipe recipe) {
        return longIdTestPresenceChecker.hasId(recipe);
    }

    private boolean hasImage(Recipe recipe) {
        return imageTestPresenceChecker.hasImage(recipe);
    }

    private boolean hasName(Recipe recipe) {
        return stringNameTestPresenceChecker.hasName(recipe);
    }

    private boolean hasDescription(Recipe recipe) {
        return descriptionTestPresenceChecker.hasDescription(recipe);
    }

    private boolean hasDifficulty(Recipe recipe) {
        return recipeDifficultyTestPresenceChecker.hasDifficulty(recipe);
    }

    private boolean hasCategories(Recipe recipe) {
        final Set<Category> categories = recipe.getCategories();
        for (Category category : categories) {
            final boolean wasCreated = categoryTestPersistenceChecker.wasCreated(category);
            final boolean wasUpdated = categoryTestPersistenceChecker.wasUpdated(category);
            if (!wasCreated && !wasUpdated) {
                return false;
            }
        }
        return true;
    }

    private boolean hasIngredients(Recipe recipe) {
        final Set<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            final boolean wasCreated = ingredientTestPersistenceChecker.wasCreated(ingredient);
            final boolean wasUpdated = ingredientTestPersistenceChecker.wasUpdated(ingredient);
            if (!wasCreated && !wasUpdated) {
                return false;
            }
        }
        return true;
    }

    private boolean hasServings(Recipe recipe) {
        return servingsTestPresenceChecker.hasServings(recipe);
    }

    private boolean hasCookingTime(Recipe recipe) {
        return cookingTimeTestPresenceChecker.hasCookingTime(recipe);
    }

    private boolean hasInstructions(Recipe recipe) {
        return instructionsTestPresenceChecker.hasInstructions(recipe);
    }

    private boolean hasCreatedAt(Recipe recipe) {
        return simpleAuditTestPresenceChecker.hasCreatedAt(recipe);
    }

    private boolean hasCreatedBy(Recipe recipe) {
        return simpleAuditTestPresenceChecker.hasCreatedBy(recipe);
    }

    private boolean doesNotHaveUpdatedAt(Recipe recipe) {
        return !hasUpdatedAt(recipe);
    }

    private boolean hasUpdatedAt(Recipe recipe) {
        return simpleAuditTestPresenceChecker.hasUpdatedAt(recipe);
    }

    private boolean doesNotHaveUpdatedBy(Recipe recipe) {
        return !hasUpdatedBy(recipe);
    }

    private boolean hasUpdatedBy(Recipe recipe) {
        return simpleAuditTestPresenceChecker.hasUpdatedBy(recipe);
    }

    private boolean hasVersion(Recipe recipe) {
        return longVersionTestPresenceChecker.hasVersion(recipe);
    }
}
