package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.*;
import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.factory.RecipeDTOTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RandomRecipeDTOTestFactoryTestConfiguration.class,
        FakerTestConfiguration.class,
        RandomDifficultyDTOTestFactoryTestConfiguration.class,
        RandomCategoryDTOTestFactoryTestConfiguration.class,
        RandomIngredientDTOTestFactoryTestConfiguration.class,
        RandomMeasureUnitDTOTestFactoryTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("RandomRecipeDTOTestFactory integration test")
class RandomRecipeDTOTestFactoryIntegrationTest {

    private final RecipeDTOTestFactory factory;

    @Test
    @DisplayName("createRecipeDTO method should return random RecipeDTO object")
    void createRecipeDTO_shouldReturnRandomRecipeDTO() {
        final RecipeDTO recipeDTO = factory.createRecipeDTO();
        final Long id = recipeDTO.getId();
        final byte[] image = recipeDTO.getImage();
        final String name = recipeDTO.getName();
        final String description = recipeDTO.getDescription();
        final DifficultyDTO difficultyDTO = recipeDTO.getDifficultyDTO();
        final Set<CategoryDTO> categoryDTOs = recipeDTO.getCategoryDTOs();
        final Set<IngredientDTO> ingredientDTOs = recipeDTO.getIngredientDTOs();
        final Double servings = recipeDTO.getServings();
        final Integer cookingTime = recipeDTO.getCookingTime();
        final String instructions = recipeDTO.getInstructions();
        final Long version = recipeDTO.getVersion();

        assertNotNull(recipeDTO);
        assertNotNull(id);
        assertNotNull(image);
        assertNotNull(name);
        assertNotNull(description);
        assertNotNull(difficultyDTO);
        assertNotNull(categoryDTOs);
        assertNotNull(ingredientDTOs);
        assertNotNull(servings);
        assertNotNull(cookingTime);
        assertNotNull(instructions);
        assertNotNull(version);
    }
}