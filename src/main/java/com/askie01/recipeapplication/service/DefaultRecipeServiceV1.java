package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.mapper.RecipeDTOToRecipeMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RequiredArgsConstructor
public class DefaultRecipeServiceV1 implements RecipeServiceV1 {

    private final RecipeRepository repository;
    private final RecipeDTOToRecipeMapper mapper;

    @Override
    public Recipe createRecipe(RecipeDTO recipeDTO) {
        final boolean recipeIsNew = recipeIsNew(recipeDTO);
        if (recipeIsNew) {
            final boolean missImage = recipeDTO.getImage() == null;
            if (missImage) {
                final byte[] defaultImage = getDefaultImage();
                recipeDTO.setImage(defaultImage);
            }
            final Recipe recipe = mapper.mapToEntity(recipeDTO);
            return repository.save(recipe);
        }
        throw new IllegalArgumentException("Unable to save recipe: " + recipeDTO);
    }

    private boolean recipeIsNew(RecipeDTO recipeDTO) {
        final boolean recipeDoesNotHaveId = recipeDTO.getId() == null;
        final boolean recipeDoesNotHaveVersion = recipeDTO.getVersion() == null;
        return recipeDoesNotHaveId && recipeDoesNotHaveVersion;
    }

    @SneakyThrows
    public byte[] getDefaultImage() {
        final File defaultImageFile = new File("src/main/resources/static/default-recipe.png");
        try (final FileInputStream defaultImageStream = new FileInputStream(defaultImageFile)) {
            return defaultImageStream.readAllBytes();
        }
    }

    @Override
    public Recipe getRecipe(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public List<Recipe> getRecipes() {
        return repository.findAll();
    }

    @Override
    public Recipe updateRecipe(RecipeDTO recipeDTO) {
        final Long id = recipeDTO.getId();
        final Recipe recipe = getRecipe(id);
        mapper.map(recipeDTO, recipe);
        return repository.save(recipe);
    }

    @Override
    public Recipe deleteRecipe(Long id) {
        final Recipe recipe = getRecipe(id);
        repository.deleteById(id);
        return recipe;
    }
}
