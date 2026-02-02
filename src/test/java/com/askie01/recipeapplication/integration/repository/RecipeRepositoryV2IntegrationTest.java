package com.askie01.recipeapplication.integration.repository;

import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.repository.RecipeRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RecipeRepositoryV2 integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RecipeRepositoryV2IntegrationTest {

    private final RecipeRepositoryV2 repository;

    @Test
    @DisplayName("findDistinctRecipes when page request is present returns page of recipes")
    void findDistinctRecipes_whenPageRequestIsPresent_returnsPageOfRecipes() {
        final Pageable pageRequest = PageRequest.of(0, 10);
        final boolean recipesExist = !repository.findDistinctRecipes(pageRequest)
                .stream()
                .toList()
                .isEmpty();
        assertTrue(recipesExist);
    }

    @Test
    @DisplayName("findDistinctRecipes when page request is null returns page of recipes")
    void findDistinctRecipes_whenPageRequestIsNull_returnsPageOfRecipes() {
        final Page<Recipe> recipes = repository.findDistinctRecipes(null);
        assertNotNull(recipes);
    }

    @Test
    @DisplayName("search when text is present returns page of recipes")
    void search_whenTextIsPresent_returnsPageOfRecipes() {
        final String randomName = repository.findAll().stream().findAny().get().getName();
        final Pageable pageRequest = PageRequest.of(0, 10);
        final boolean recipesExist = !repository.search(randomName, pageRequest)
                .stream()
                .toList()
                .isEmpty();
        assertTrue(recipesExist);
    }

    @Test
    @DisplayName("search when text is null returns page of recipes")
    void search_whenTextIsNull_returnsPageOfRecipes() {
        final Page<Recipe> recipes = repository.search(null, null);
        assertNotNull(recipes);
    }
}