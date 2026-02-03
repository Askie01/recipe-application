package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.IngredientResponseBody;
import com.askie01.recipeapplication.dto.RecipeResponseBody;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultRecipeToRecipeResponseBodyMapper implements RecipeToRecipeResponseBodyMapper {

    @Override
    public RecipeResponseBody mapToDTO(Recipe recipe) {
        final RecipeResponseBody recipeResponseBody = new RecipeResponseBody();
        map(recipe, recipeResponseBody);
        return recipeResponseBody;
    }

    @Override
    public void map(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        mapId(recipe, recipeResponseBody);
        mapName(recipe, recipeResponseBody);
        mapImage(recipe, recipeResponseBody);
        mapDescription(recipe, recipeResponseBody);
        mapDifficulty(recipe, recipeResponseBody);
        mapCategories(recipe, recipeResponseBody);
        mapIngredients(recipe, recipeResponseBody);
        mapServings(recipe, recipeResponseBody);
        mapCookingTime(recipe, recipeResponseBody);
        mapInstructions(recipe, recipeResponseBody);
    }

    private static void mapId(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        final Long id = recipe.getId();
        recipeResponseBody.setId(id);
    }

    private static void mapName(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        final String name = recipe.getName();
        recipeResponseBody.setName(name);
    }

    private static void mapImage(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        final byte[] image = recipe.getImage();
        recipeResponseBody.setImage(image);
    }

    private static void mapDescription(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        final String description = recipe.getDescription();
        recipeResponseBody.setDescription(description);
    }

    private static void mapDifficulty(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        final Difficulty difficulty = recipe.getDifficulty();
        recipeResponseBody.setDifficulty(difficulty);
    }

    private static void mapCategories(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        final Set<String> categories = recipe.getCategories()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toCollection(HashSet::new));
        recipeResponseBody.setCategories(categories);
    }

    private static void mapIngredients(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        final Set<IngredientResponseBody> ingredients = recipe.getIngredients()
                .stream()
                .map(ingredient -> {
                    final String name = ingredient.getName();
                    final Double amount = ingredient.getAmount();
                    final String unit = ingredient.getMeasureUnit().getName();
                    return new IngredientResponseBody(name, amount, unit);
                })
                .collect(Collectors.toCollection(HashSet::new));
        recipeResponseBody.setIngredients(ingredients);
    }

    private static void mapServings(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        final Double servings = recipe.getServings();
        recipeResponseBody.setServings(servings);
    }

    private static void mapCookingTime(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        final Integer cookingTime = recipe.getCookingTime();
        recipeResponseBody.setCookingTime(cookingTime);
    }

    private static void mapInstructions(Recipe recipe, RecipeResponseBody recipeResponseBody) {
        final String instructions = recipe.getInstructions();
        recipeResponseBody.setInstructions(instructions);
    }
}
