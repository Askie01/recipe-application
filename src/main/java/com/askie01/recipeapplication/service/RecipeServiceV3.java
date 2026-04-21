package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.CustomerRecipeRequestBody;
import com.askie01.recipeapplication.model.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface RecipeServiceV3 {

    Long createRecipe(String username, CustomerRecipeRequestBody requestBody);

    Recipe getRecipe(Long id);

    byte[] getRecipeImage(Long id);

    Page<Recipe> getCustomerRecipes(String username, Pageable pageable);

    Page<Recipe> searchRecipes(String query, Pageable pageable);

    void updateRecipe(String username, Long id, CustomerRecipeRequestBody requestBody);

    void updateImage(String username, Long id, MultipartFile image);

    void restoreDefaultImage(String username, Long id);

    void deleteRecipe(String username, Long id);
}
