package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CustomerRecipeResponseBody;
import com.askie01.recipeapplication.dto.IngredientResponseBody;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultRecipeToCustomerRecipeResponseBodyMapper implements RecipeToCustomerRecipeResponseBodyMapper {

    @Override
    public CustomerRecipeResponseBody mapToDTO(Recipe recipe) {
        final CustomerRecipeResponseBody customerRecipeResponseBody = new CustomerRecipeResponseBody();
        map(recipe, customerRecipeResponseBody);
        return customerRecipeResponseBody;
    }

    @Override
    public void map(Recipe recipe, CustomerRecipeResponseBody responseBody) {
        mapId(recipe, responseBody);
        mapName(recipe, responseBody);
        mapDescription(recipe, responseBody);
        mapDifficulty(recipe, responseBody);
        mapCategories(recipe, responseBody);
        mapIngredients(recipe, responseBody);
        mapServings(recipe, responseBody);
        mapCookingTime(recipe, responseBody);
        mapInstructions(recipe, responseBody);
    }

    private void mapId(Recipe recipe, CustomerRecipeResponseBody responseBody) {
        final Long id = recipe.getId();
        responseBody.setId(id);
    }

    private void mapName(Recipe recipe, CustomerRecipeResponseBody responseBody) {
        final String name = recipe.getName();
        responseBody.setName(name);
    }

    private void mapDifficulty(Recipe recipe, CustomerRecipeResponseBody responseBody) {
        final Difficulty difficulty = recipe.getDifficulty();
        responseBody.setDifficulty(difficulty);
    }

    private void mapCategories(Recipe recipe, CustomerRecipeResponseBody responseBody) {
        final Set<String> categories = recipe.getCategories()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toCollection(HashSet::new));
        responseBody.setCategories(categories);
    }

    private void mapIngredients(Recipe recipe, CustomerRecipeResponseBody responseBody) {
        final Set<IngredientResponseBody> ingredients = recipe.getIngredients()
                .stream()
                .map(ingredient -> {
                    final String name = ingredient.getName();
                    final Double amount = ingredient.getAmount();
                    final String unit = ingredient.getMeasureUnit().getName();
                    return new IngredientResponseBody(name, amount, unit);
                })
                .collect(Collectors.toCollection(HashSet::new));
        responseBody.setIngredients(ingredients);
    }

    private void mapServings(Recipe recipe, CustomerRecipeResponseBody responseBody) {
        final Double servings = recipe.getServings();
        responseBody.setServings(servings);
    }

    private void mapCookingTime(Recipe recipe, CustomerRecipeResponseBody responseBody) {
        final Integer cookingTime = recipe.getCookingTime();
        responseBody.setCookingTime(cookingTime);
    }

    private void mapDescription(Recipe recipe, CustomerRecipeResponseBody responseBody) {
        final String description = recipe.getDescription();
        responseBody.setDescription(description);
    }

    private void mapInstructions(Recipe recipe, CustomerRecipeResponseBody responseBody) {
        final String instructions = recipe.getInstructions();
        responseBody.setInstructions(instructions);
    }
}
