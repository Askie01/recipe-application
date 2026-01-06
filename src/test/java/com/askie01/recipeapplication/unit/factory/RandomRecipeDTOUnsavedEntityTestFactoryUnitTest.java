package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.factory.*;
import com.github.javafaker.Faker;
import com.github.javafaker.FunnyName;
import com.github.javafaker.Lorem;
import com.github.javafaker.Number;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomRecipeDTOUnsavedEntityTestFactory unit test")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomRecipeDTOUnsavedEntityTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private Number fakerNumber;

    @Mock
    private FunnyName fakerFunnyName;

    @Mock
    private Lorem fakerLorem;

    @Mock
    private DifficultyDTOTestFactory difficultyDTOTestFactory;

    @Mock
    private CategoryDTOUnsavedEntityTestFactory categoryDTOUnsavedEntityTestFactory;

    @Mock
    private IngredientDTOUnsavedEntityTestFactory ingredientDTOUnsavedEntityTestFactory;
    private RecipeDTOUnsavedEntityTestFactory recipeDTOUnsavedEntityTestFactory;

    @BeforeEach
    void setUp() {
        this.recipeDTOUnsavedEntityTestFactory = new RandomRecipeDTOUnsavedEntityTestFactory(
                faker,
                difficultyDTOTestFactory,
                categoryDTOUnsavedEntityTestFactory,
                ingredientDTOUnsavedEntityTestFactory
        );
    }

    @Test
    @DisplayName("createRecipeDTO method should return random RecipeDTO entity-like object")
    void createRecipeDTO_shouldReturnRandomRecipeDTOUnsavedEntity() {
        when(faker.number()).thenReturn(fakerNumber);
        when(faker.lorem()).thenReturn(fakerLorem);
        when(fakerLorem.characters(anyInt())).thenReturn("Random text");
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Random name");
        when(difficultyDTOTestFactory.createDifficultyDTO()).thenReturn(DifficultyDTO.EASY);
        when(categoryDTOUnsavedEntityTestFactory.createCategoryDTO()).thenReturn(
                CategoryDTO.builder()
                        .id(1L)
                        .name("Random categoryDTO name")
                        .version(1L)
                        .build()
        );
        when(ingredientDTOUnsavedEntityTestFactory.createIngredientDTO()).thenReturn(
                IngredientDTO.builder()
                        .id(1L)
                        .name("Random ingredientDTO name")
                        .amount(1.0)
                        .build()
        );
        when(fakerNumber.randomDouble(anyInt(), anyInt(), anyInt())).thenReturn(10.5);
        when(fakerNumber.numberBetween(anyInt(), anyInt())).thenReturn(10);

        final RecipeDTO recipeDTO = recipeDTOUnsavedEntityTestFactory.createRecipeDTO();
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
        assertNull(id);
        assertNotNull(image);
        assertNotNull(name);
        assertNotNull(description);
        assertNotNull(difficultyDTO);
        assertNotNull(categoryDTOs);
        assertNotNull(ingredientDTOs);
        assertNotNull(servings);
        assertNotNull(cookingTime);
        assertNotNull(instructions);
        assertNull(version);
    }
}