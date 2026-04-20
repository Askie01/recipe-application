package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.IngredientResponseBody;
import com.askie01.recipeapplication.dto.SearchRecipeResponseBody;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.askie01.recipeapplication.repository.RecipeRepositoryV3;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DefaultRecipeToSearchRecipeResponseBodyMapper implements RecipeToSearchRecipeResponseBodyMapper {

    private final RecipeRepositoryV3 repository;

    @Override
    public SearchRecipeResponseBody mapToDTO(Recipe recipe) {
        final SearchRecipeResponseBody searchRecipeResponseBody = new SearchRecipeResponseBody();
        map(recipe, searchRecipeResponseBody);
        return searchRecipeResponseBody;
    }

    @Override
    public void map(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        mapId(recipe, searchRecipeResponseBody);
        mapName(recipe, searchRecipeResponseBody);
        mapDescription(recipe, searchRecipeResponseBody);
        mapDifficulty(recipe, searchRecipeResponseBody);
        mapCategories(recipe, searchRecipeResponseBody);
        mapIngredients(recipe, searchRecipeResponseBody);
        mapServings(recipe, searchRecipeResponseBody);
        mapCookingTime(recipe, searchRecipeResponseBody);
        mapInstructions(recipe, searchRecipeResponseBody);
        mapOwner(recipe, searchRecipeResponseBody);
    }

    private void mapOwner(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        final Long recipeId = recipe.getId();
        final String owner = repository
                .findOwner(recipeId)
                .orElseThrow(() -> new UsernameNotFoundException("Recipe with id: " + recipeId + " does not have owner"));
        searchRecipeResponseBody.setOwner(owner);
    }

    private void mapId(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        final Long id = recipe.getId();
        searchRecipeResponseBody.setId(id);
    }

    private void mapName(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        final String name = recipe.getName();
        searchRecipeResponseBody.setName(name);
    }

    private void mapDescription(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        final String description = recipe.getDescription();
        searchRecipeResponseBody.setDescription(description);
    }

    private void mapDifficulty(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        final Difficulty difficulty = recipe.getDifficulty();
        searchRecipeResponseBody.setDifficulty(difficulty);
    }

    private void mapCategories(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        final Set<String> categories = recipe.getCategories()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toCollection(HashSet::new));
        searchRecipeResponseBody.setCategories(categories);
    }

    private void mapIngredients(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        final Set<IngredientResponseBody> ingredients = recipe.getIngredients()
                .stream()
                .map(this::mapToIngredientResponseBody)
                .collect(Collectors.toCollection(HashSet::new));
        searchRecipeResponseBody.setIngredients(ingredients);
    }

    private IngredientResponseBody mapToIngredientResponseBody(Ingredient ingredient) {
        final String name = ingredient.getName();
        final Double amount = ingredient.getAmount();
        final String unit = ingredient.getMeasureUnit().getName();
        return IngredientResponseBody.builder()
                .name(name)
                .amount(amount)
                .unit(unit)
                .build();
    }

    private void mapServings(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        final Double servings = recipe.getServings();
        searchRecipeResponseBody.setServings(servings);
    }

    private void mapCookingTime(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        final Integer cookingTime = recipe.getCookingTime();
        searchRecipeResponseBody.setCookingTime(cookingTime);
    }

    private void mapInstructions(Recipe recipe, SearchRecipeResponseBody searchRecipeResponseBody) {
        final String instructions = recipe.getInstructions();
        searchRecipeResponseBody.setInstructions(instructions);
    }
}
