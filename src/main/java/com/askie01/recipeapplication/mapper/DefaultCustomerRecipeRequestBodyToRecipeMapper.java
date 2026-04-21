package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CustomerRecipeRequestBody;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultCustomerRecipeRequestBodyToRecipeMapper implements CustomerRecipeRequestBodyToRecipeMapper {

    @Override
    public Recipe mapToEntity(CustomerRecipeRequestBody requestBody) {
        final Recipe recipe = Recipe.builder()
                .categories(new HashSet<>())
                .ingredients(new HashSet<>())
                .build();
        map(requestBody, recipe);
        return recipe;
    }

    @Override
    public void map(CustomerRecipeRequestBody requestBody, Recipe recipe) {
        mapName(requestBody, recipe);
        mapDescription(requestBody, recipe);
        mapDifficulty(requestBody, recipe);
        mapCategories(requestBody, recipe);
        mapIngredients(requestBody, recipe);
        mapServings(requestBody, recipe);
        mapCookingTime(requestBody, recipe);
        mapInstructions(requestBody, recipe);
    }

    private void mapName(CustomerRecipeRequestBody requestBody, Recipe recipe) {
        final String name = requestBody.getName();
        recipe.setName(name);
    }

    private void mapDescription(CustomerRecipeRequestBody requestBody, Recipe recipe) {
        final String description = requestBody.getDescription();
        recipe.setDescription(description);
    }

    private void mapDifficulty(CustomerRecipeRequestBody requestBody, Recipe recipe) {
        final Difficulty difficulty = requestBody.getDifficulty();
        recipe.setDifficulty(difficulty);
    }

    private void mapCategories(CustomerRecipeRequestBody requestBody, Recipe recipe) {
        final Set<Category> categories = requestBody.getCategories()
                .stream()
                .map(categoryName -> Category.builder().name(categoryName).build())
                .collect(Collectors.toCollection(HashSet::new));
        recipe.getCategories().clear();
        recipe.getCategories().addAll(categories);
    }

    private void mapIngredients(CustomerRecipeRequestBody requestBody, Recipe recipe) {
        final Set<Ingredient> ingredients = requestBody.getIngredients()
                .stream()
                .map(ingredientRequestBody -> {
                    final String ingredientName = ingredientRequestBody.getName();
                    final Double amount = ingredientRequestBody.getAmount();
                    final String unit = ingredientRequestBody.getUnit();

                    final MeasureUnit measureUnit = MeasureUnit.builder()
                            .name(unit)
                            .build();
                    return Ingredient.builder()
                            .name(ingredientName)
                            .amount(amount)
                            .measureUnit(measureUnit)
                            .build();
                })
                .collect(Collectors.toCollection(HashSet::new));
        recipe.getIngredients().clear();
        recipe.getIngredients().addAll(ingredients);
    }

    private void mapServings(CustomerRecipeRequestBody requestBody, Recipe recipe) {
        final Double servings = requestBody.getServings();
        recipe.setServings(servings);
    }

    private void mapCookingTime(CustomerRecipeRequestBody requestBody, Recipe recipe) {
        final Integer cookingTime = requestBody.getCookingTime();
        recipe.setCookingTime(cookingTime);
    }

    private void mapInstructions(CustomerRecipeRequestBody requestBody, Recipe recipe) {
        final String instructions = requestBody.getInstructions();
        recipe.setInstructions(instructions);
    }
}
