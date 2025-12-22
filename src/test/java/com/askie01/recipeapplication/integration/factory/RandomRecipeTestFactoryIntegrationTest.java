package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.factory.RecipeTestFactory;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RandomRecipeTestFactoryDefaultTestConfiguration.class,
        RandomDifficultyTestFactoryDefaultTestConfiguration.class,
        RandomCategoryTestFactoryDefaultTestConfiguration.class,
        RandomIngredientTestFactoryDefaultTestConfiguration.class,
        RandomMeasureUnitTestFactoryDefaultTestConfiguration.class,
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("RandomRecipeTestFactory integration test")
class RandomRecipeTestFactoryIntegrationTest {

    private final RecipeTestFactory factory;

    @Test
    @DisplayName("createRecipe method should return random Recipe object")
    void createRecipe_shouldReturnRandomRecipe() {
        final Recipe recipe = factory.createRecipe();
        final Long id = recipe.getId();
        final byte[] image = recipe.getImage();
        final String name = recipe.getName();
        final String description = recipe.getDescription();
        final Difficulty difficulty = recipe.getDifficulty();
        final Set<Category> categories = recipe.getCategories();
        final Set<Ingredient> ingredients = recipe.getIngredients();
        final Double servings = recipe.getServings();
        final Integer cookingTime = recipe.getCookingTime();
        final String instructions = recipe.getInstructions();
        final LocalDateTime createdAt = recipe.getCreatedAt();
        final String createdBy = recipe.getCreatedBy();
        final LocalDateTime updatedAt = recipe.getUpdatedAt();
        final String updatedBy = recipe.getUpdatedBy();
        final Long version = recipe.getVersion();

        assertNotNull(recipe);
        assertNotNull(id);
        assertNotNull(image);
        assertNotNull(name);
        assertNotNull(description);
        assertNotNull(difficulty);
        assertNotNull(categories);
        assertNotNull(ingredients);
        assertNotNull(servings);
        assertNotNull(cookingTime);
        assertNotNull(instructions);
        assertNotNull(createdAt);
        assertNotNull(createdBy);
        assertNotNull(updatedAt);
        assertNotNull(updatedBy);
        assertNotNull(version);
    }
}