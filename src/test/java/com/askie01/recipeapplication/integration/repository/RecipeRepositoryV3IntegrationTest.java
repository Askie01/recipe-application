package com.askie01.recipeapplication.integration.repository;

import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.repository.RecipeRepositoryV3;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RecipeRepositoryV3 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RecipeRepositoryV3IntegrationTest {

    private final RecipeRepositoryV3 recipeRepository;
    private final CustomerRepositoryV1 customerRepository;

    @Test
    @DisplayName("findByUsername when username exists returns page of customer recipes")
    void findByUsername_whenUsernameExists_returnsCustomerRecipePage() {
        final String username = customerRepository.findAll().stream().findAny().orElseThrow().getUsername();
        final Pageable pageable = PageRequest.of(0, 10);
        final boolean recipesExist = recipeRepository
                .findByUsername(username, pageable)
                .getTotalElements() != 0;
        assertTrue(recipesExist);
    }

    @Test
    @DisplayName("findByUsername when username does not exist returns empty page")
    void findByUsername_whenUsernameDoesNotExist_returnsEmptyPage() {
        final String nonExistingUsername = "non-existing-username";
        final Pageable pageable = PageRequest.of(0, 10);
        final boolean recipesExist = recipeRepository
                .findByUsername(nonExistingUsername, pageable)
                .getTotalElements() == 0;
        assertTrue(recipesExist);
    }

    @Test
    @DisplayName("searchRecipes when query exists returns page of recipes")
    void searchRecipes_whenQueryExists_returnsRecipesPage() {
        final String query = recipeRepository.findAll().stream().findAny().orElseThrow().getName();
        final Pageable pageable = PageRequest.of(0, 10);
        final boolean recipesExist = recipeRepository
                .searchRecipes(query, pageable)
                .getTotalElements() != 0;
        assertTrue(recipesExist);
    }

    @Test
    @DisplayName("searchRecipes when query does not exist returns empty page")
    void searchRecipes_whenQueryDoesNotExist_returnsEmptyPage() {
        final String query = "non-existing-query";
        final Pageable pageable = PageRequest.of(0, 10);
        final boolean recipesExist = recipeRepository
                .searchRecipes(query, pageable)
                .getTotalElements() == 0;
        assertTrue(recipesExist);
    }

    @Test
    @DisplayName("findOwner when recipeId exists returns recipe owner")
    void findOwner_whenRecipeIdExists_returnsRecipeOwner() {
        final Long id = recipeRepository.findAll().stream().findAny().orElseThrow().getId();
        final boolean ownerExists = recipeRepository.findOwner(id).isPresent();
        assertTrue(ownerExists);
    }

    @Test
    @DisplayName("findOwner when recipeId does not exist returns empty optional")
    void findOwner_whenRecipeIdDoesNotExists_returnsEmptyOptional() {
        final Long id = Long.MAX_VALUE;
        final boolean ownerExists = recipeRepository.findOwner(id).isPresent();
        assertFalse(ownerExists);
    }

    @Test
    @DisplayName("findRecipeImage when recipeId exists returns recipe image")
    void findRecipeImage_whenRecipeIdExists_returnsRecipeImage() {
        final Long id = recipeRepository.findAll().stream().findAny().orElseThrow().getId();
        final boolean imageExists = recipeRepository.findRecipeImage(id).isPresent();
        assertTrue(imageExists);
    }

    @Test
    @DisplayName("findRecipeImage when recipeId does not exist returns empty optional")
    void findRecipeImage_whenRecipeIdDoesNotExists_returnsEmptyOptional() {
        final Long id = Long.MAX_VALUE;
        final boolean imageExists = recipeRepository.findRecipeImage(id).isPresent();
        assertFalse(imageExists);
    }
}