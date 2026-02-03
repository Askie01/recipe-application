package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultRecipeRequestBodyToRecipeMapper implements RecipeRequestBodyToRecipeMapper {

    @Override
    public Recipe mapToEntity(RecipeRequestBody requestBody) {
        final Recipe recipe = new Recipe();
        recipe.setCategories(new HashSet<>());
        recipe.setIngredients(new HashSet<>());
        map(requestBody, recipe);
        return recipe;
    }

    @Override
    public void map(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        mapName(recipeRequestBody, recipe);
        mapImage(recipeRequestBody, recipe);
        mapDescription(recipeRequestBody, recipe);
        mapDifficulty(recipeRequestBody, recipe);
        mapCategories(recipeRequestBody, recipe);
        mapIngredients(recipeRequestBody, recipe);
        mapServings(recipeRequestBody, recipe);
        mapCookingTime(recipeRequestBody, recipe);
        mapInstructions(recipeRequestBody, recipe);
    }

    private static void mapName(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        final String name = recipeRequestBody.getName();
        recipe.setName(name);
    }

    private static void mapImage(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        final byte[] image = recipeRequestBody.getImage();
        recipe.setImage(image);
    }

    private static void mapDescription(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        final String description = recipeRequestBody.getDescription();
        recipe.setDescription(description);
    }

    private static void mapDifficulty(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        final Difficulty difficulty = recipeRequestBody.getDifficulty();
        recipe.setDifficulty(difficulty);
    }

    private static void mapCategories(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        final Set<Category> categories = recipeRequestBody.getCategories()
                .stream()
                .map(categoryName -> Category.builder().name(categoryName).build())
                .collect(Collectors.toCollection(HashSet::new));
        recipe.getCategories().clear();
        recipe.getCategories().addAll(categories);
    }

    private static void mapIngredients(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        final Set<Ingredient> ingredients = recipeRequestBody.getIngredients()
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

    private static void mapServings(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        final Double servings = recipeRequestBody.getServings();
        recipe.setServings(servings);
    }

    private static void mapCookingTime(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        final Integer cookingTime = recipeRequestBody.getCookingTime();
        recipe.setCookingTime(cookingTime);
    }

    private static void mapInstructions(RecipeRequestBody recipeRequestBody, Recipe recipe) {
        final String instructions = recipeRequestBody.getInstructions();
        recipe.setInstructions(instructions);
    }
}
