package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SimpleRecipeToRecipeDTOMapper implements RecipeToRecipeDTOMapper {

    private final LongIdMapper longIdMapper;
    private final ImageMapper imageMapper;
    private final StringNameMapper stringNameMapper;
    private final DescriptionMapper descriptionMapper;
    private final DifficultyToDifficultyDTOMapper difficultyToDifficultyDTOMapper;
    private final CategoryToCategoryDTOMapper categoryToCategoryDTOMapper;
    private final IngredientToIngredientDTOMapper ingredientToIngredientDTOMapper;
    private final ServingsMapper servingsMapper;
    private final CookingTimeMapper cookingTimeMapper;
    private final InstructionsMapper instructionsMapper;
    private final LongVersionMapper longVersionMapper;

    @Override
    public RecipeDTO mapToDTO(Recipe recipe) {
        final RecipeDTO recipeDTO = new RecipeDTO();
        map(recipe, recipeDTO);
        return recipeDTO;
    }

    @Override
    public void map(Recipe recipe, RecipeDTO recipeDTO) {
        mapId(recipe, recipeDTO);
        mapImage(recipe, recipeDTO);
        mapName(recipe, recipeDTO);
        mapDescription(recipe, recipeDTO);
        mapDifficultyToDifficultyDTO(recipe, recipeDTO);
        mapCategoriesToCategoryDTOs(recipe, recipeDTO);
        mapIngredientsToIngredientDTOs(recipe, recipeDTO);
        mapServings(recipe, recipeDTO);
        mapCookingTime(recipe, recipeDTO);
        mapInstructions(recipe, recipeDTO);
        mapVersion(recipe, recipeDTO);
    }

    private void mapId(Recipe recipe, RecipeDTO recipeDTO) {
        longIdMapper.map(recipe, recipeDTO);
    }

    private void mapImage(Recipe recipe, RecipeDTO recipeDTO) {
        imageMapper.map(recipe, recipeDTO);
    }

    private void mapName(Recipe recipe, RecipeDTO recipeDTO) {
        stringNameMapper.map(recipe, recipeDTO);
    }

    private void mapDescription(Recipe recipe, RecipeDTO recipeDTO) {
        descriptionMapper.map(recipe, recipeDTO);
    }

    private void mapDifficultyToDifficultyDTO(Recipe recipe, RecipeDTO recipeDTO) {
        final Difficulty difficulty = recipe.getDifficulty();
        final DifficultyDTO difficultyDTO = difficultyToDifficultyDTOMapper.mapToValue(difficulty);
        recipeDTO.setDifficultyDTO(difficultyDTO);
    }

    private void mapCategoriesToCategoryDTOs(Recipe recipe, RecipeDTO recipeDTO) {
        final Set<CategoryDTO> categoryDTOs = recipe.getCategories().stream()
                .map(categoryToCategoryDTOMapper::mapToDTO)
                .collect(Collectors.toCollection(HashSet::new));
        final boolean hasCategories = recipeDTO.getCategoryDTOs() != null;
        if (hasCategories) {
            recipeDTO.getCategoryDTOs().clear();
            recipeDTO.getCategoryDTOs().addAll(categoryDTOs);
        } else {
            recipeDTO.setCategoryDTOs(categoryDTOs);
        }
    }

    private void mapIngredientsToIngredientDTOs(Recipe recipe, RecipeDTO recipeDTO) {
        final Set<IngredientDTO> ingredientDTOs = recipe.getIngredients().stream()
                .map(ingredientToIngredientDTOMapper::mapToDTO)
                .collect(Collectors.toCollection(HashSet::new));
        final boolean hasIngredients = recipeDTO.getIngredientDTOs() != null;
        if (hasIngredients) {
            recipeDTO.getIngredientDTOs().clear();
            recipeDTO.getIngredientDTOs().addAll(ingredientDTOs);
        } else {
            recipeDTO.setIngredientDTOs(ingredientDTOs);
        }
    }

    private void mapServings(Recipe recipe, RecipeDTO recipeDTO) {
        servingsMapper.map(recipe, recipeDTO);
    }

    private void mapCookingTime(Recipe recipe, RecipeDTO recipeDTO) {
        cookingTimeMapper.map(recipe, recipeDTO);
    }

    private void mapInstructions(Recipe recipe, RecipeDTO recipeDTO) {
        instructionsMapper.map(recipe, recipeDTO);
    }

    private void mapVersion(Recipe recipe, RecipeDTO recipeDTO) {
        longVersionMapper.map(recipe, recipeDTO);
    }
}
