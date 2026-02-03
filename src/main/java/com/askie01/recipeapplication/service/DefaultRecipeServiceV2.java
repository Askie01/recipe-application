package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.mapper.RecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.repository.RecipeRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class DefaultRecipeServiceV2 implements RecipeServiceV2 {

    private final RecipeRepositoryV2 repository;
    private final RecipeRequestBodyToRecipeMapper mapper;

    @Override
    public void createRecipe(RecipeRequestBody requestBody) {
        final Recipe recipe = mapper.mapToEntity(requestBody);
        final boolean missImage = requestBody.getImage() == null;
        if (missImage) {
            final byte[] defaultImage = getDefaultImage();
            recipe.setImage(defaultImage);
        }
        repository.save(recipe);
    }

    @Override
    public Recipe getRecipe(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public List<Recipe> getRecipes(Integer pageNumber, Integer pageSize) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository.findDistinctRecipes(pageable).toList();
    }

    @Override
    public List<Recipe> searchRecipes(String text, Integer pageNumber, Integer pageSize) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return repository.search(text, pageable).toList();
    }

    @Override
    public void updateRecipe(Long id, RecipeRequestBody requestBody) {
        final Recipe recipe = repository
                .findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        final boolean removedImage = requestBody.getImage() == null;
        if (removedImage) {
            final byte[] defaultImage = getDefaultImage();
            requestBody.setImage(defaultImage);
        }
        mapper.map(requestBody, recipe);
        repository.save(recipe);
    }

    @SneakyThrows
    private byte[] getDefaultImage() {
        return new ClassPathResource("static/default-recipe.png")
                .getInputStream()
                .readAllBytes();
    }

    @Override
    public void deleteRecipe(Long id) {
        repository.deleteById(id);
    }
}
