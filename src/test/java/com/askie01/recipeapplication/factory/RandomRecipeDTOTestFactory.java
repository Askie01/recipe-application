package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RandomRecipeDTOTestFactory implements RecipeDTOTestFactory {

    private final Faker faker;
    private final DifficultyDTOTestFactory difficultyDTOTestFactory;
    private final CategoryDTOTestFactory categoryDTOTestFactory;
    private final IngredientDTOTestFactory ingredientDTOTestFactory;

    @Override
    public RecipeDTO createRecipeDTO() {
        final Long randomId = getRandomLong();
        final byte[] randomImage = getRandomImage();
        final String randomName = getRandomString();
        final String randomDescription = getRandomString();
        final DifficultyDTO randomDifficultyDTO = getRandomDifficultyDTO();
        final List<CategoryDTO> randomCategoryDTOs = getRandomCategoryDTOs();
        final List<IngredientDTO> randomIngredientDTOs = getRandomIngredientDTOs();
        final Double randomServings = getRandomDouble();
        final Integer randomCookingTime = getRandomInteger();
        final String randomInstructions = getRandomString();
        final Long randomVersion = getRandomLong();
        return RecipeDTO.builder()
                .id(randomId)
                .image(randomImage)
                .name(randomName)
                .description(randomDescription)
                .difficultyDTO(randomDifficultyDTO)
                .categoryDTOs(randomCategoryDTOs)
                .ingredientDTOs(randomIngredientDTOs)
                .servings(randomServings)
                .cookingTime(randomCookingTime)
                .instructions(randomInstructions)
                .version(randomVersion)
                .build();
    }

    private Long getRandomLong() {
        return faker.number().randomNumber(100000, false);
    }

    private byte[] getRandomImage() {
        return faker.lorem().characters(100000).getBytes();
    }

    private String getRandomString() {
        return faker.funnyName().name();
    }

    private DifficultyDTO getRandomDifficultyDTO() {
        return difficultyDTOTestFactory.createDifficultyDTO();
    }

    private List<CategoryDTO> getRandomCategoryDTOs() {
        return List.of(
                categoryDTOTestFactory.createCategoryDTO(),
                categoryDTOTestFactory.createCategoryDTO(),
                categoryDTOTestFactory.createCategoryDTO(),
                categoryDTOTestFactory.createCategoryDTO(),
                categoryDTOTestFactory.createCategoryDTO()
        );
    }

    private List<IngredientDTO> getRandomIngredientDTOs() {
        return List.of(
                ingredientDTOTestFactory.createIngredientDTO(),
                ingredientDTOTestFactory.createIngredientDTO(),
                ingredientDTOTestFactory.createIngredientDTO(),
                ingredientDTOTestFactory.createIngredientDTO(),
                ingredientDTOTestFactory.createIngredientDTO()
        );
    }

    private Double getRandomDouble() {
        return faker.number().randomDouble(2, 1, 100);
    }

    private Integer getRandomInteger() {
        return faker.number().numberBetween(1, 100);
    }
}
