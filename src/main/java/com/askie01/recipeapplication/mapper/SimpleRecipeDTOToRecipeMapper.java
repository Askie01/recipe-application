package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SimpleRecipeDTOToRecipeMapper implements RecipeDTOToRecipeMapper {

    private final LongIdMapper longIdMapper;
    private final ImageMapper imageMapper;
    private final StringNameMapper stringNameMapper;
    private final DescriptionMapper descriptionMapper;
    private final DifficultyDTOToDifficultyMapper difficultyDTOToDifficultyMapper;
    private final CategoryDTOToCategoryMapper categoryDTOToCategoryMapper;
    private final IngredientDTOToIngredientMapper ingredientDTOToIngredientMapper;
    private final ServingsMapper servingsMapper;
    private final CookingTimeMapper cookingTimeMapper;
    private final InstructionsMapper instructionsMapper;
    private final LongVersionMapper longVersionMapper;

    @Override
    public Recipe mapToEntity(RecipeDTO recipeDTO) {
        final Recipe recipe = new Recipe();
        map(recipeDTO, recipe);
        return recipe;
    }

    @Override
    public void map(RecipeDTO recipeDTO, Recipe recipe) {
        mapId(recipeDTO, recipe);
        mapImage(recipeDTO, recipe);
        mapName(recipeDTO, recipe);
        mapDescription(recipeDTO, recipe);
        mapDifficultyDTOToDifficulty(recipeDTO, recipe);
        mapCategoryDTOsToCategories(recipeDTO, recipe);
        mapIngredientDTOsToIngredients(recipeDTO, recipe);
        mapServings(recipeDTO, recipe);
        mapCookingTime(recipeDTO, recipe);
        mapInstructions(recipeDTO, recipe);
        mapVersions(recipeDTO, recipe);
    }

    private void mapId(RecipeDTO recipeDTO, Recipe recipe) {
        longIdMapper.map(recipeDTO, recipe);
    }

    private void mapImage(RecipeDTO recipeDTO, Recipe recipe) {
        imageMapper.map(recipeDTO, recipe);
    }

    private void mapName(RecipeDTO recipeDTO, Recipe recipe) {
        stringNameMapper.map(recipeDTO, recipe);
    }

    private void mapDescription(RecipeDTO recipeDTO, Recipe recipe) {
        descriptionMapper.map(recipeDTO, recipe);
    }

    private void mapDifficultyDTOToDifficulty(RecipeDTO recipeDTO, Recipe recipe) {
        final DifficultyDTO difficultyDTO = recipeDTO.getDifficultyDTO();
        final Difficulty difficulty = difficultyDTOToDifficultyMapper.mapToValue(difficultyDTO);
        recipe.setDifficulty(difficulty);
    }

    private void mapCategoryDTOsToCategories(RecipeDTO recipeDTO, Recipe recipe) {
        final Set<Category> categories = recipeDTO.getCategoryDTOs().stream()
                .map(categoryDTOToCategoryMapper::mapToEntity)
                .collect(Collectors.toCollection(HashSet::new));
        if (recipe.getCategories() == null) {
            recipe.setCategories(categories);
        } else {
            recipe.getCategories().clear();
            recipe.getCategories().addAll(categories);
        }
    }

    private void mapIngredientDTOsToIngredients(RecipeDTO recipeDTO, Recipe recipe) {
        final Set<Ingredient> ingredients = recipeDTO.getIngredientDTOs().stream()
                .map(ingredientDTOToIngredientMapper::mapToEntity)
                .collect(Collectors.toCollection(HashSet::new));
        if (recipe.getIngredients() == null) {
            recipe.setIngredients(ingredients);
        } else {
            recipe.getIngredients().clear();
            recipe.getIngredients().addAll(ingredients);
        }
    }

    private void mapServings(RecipeDTO recipeDTO, Recipe recipe) {
        servingsMapper.map(recipeDTO, recipe);
    }

    private void mapCookingTime(RecipeDTO recipeDTO, Recipe recipe) {
        cookingTimeMapper.map(recipeDTO, recipe);
    }

    private void mapInstructions(RecipeDTO recipeDTO, Recipe recipe) {
        instructionsMapper.map(recipeDTO, recipe);
    }

    private void mapVersions(RecipeDTO recipeDTO, Recipe recipe) {
        longVersionMapper.map(recipeDTO, recipe);
    }
}
