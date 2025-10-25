package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        final Set<CategoryDTO> randomCategoryDTOs = getRandomCategoryDTOs();
        final Set<IngredientDTO> randomIngredientDTOs = getRandomIngredientDTOs();
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

    private Set<CategoryDTO> getRandomCategoryDTOs() {
        return Stream.of(
                        categoryDTOTestFactory.createCategoryDTO(),
                        categoryDTOTestFactory.createCategoryDTO(),
                        categoryDTOTestFactory.createCategoryDTO(),
                        categoryDTOTestFactory.createCategoryDTO(),
                        categoryDTOTestFactory.createCategoryDTO()
                )
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Set<IngredientDTO> getRandomIngredientDTOs() {
        return Stream.of(
                        ingredientDTOTestFactory.createIngredientDTO(),
                        ingredientDTOTestFactory.createIngredientDTO(),
                        ingredientDTOTestFactory.createIngredientDTO(),
                        ingredientDTOTestFactory.createIngredientDTO(),
                        ingredientDTOTestFactory.createIngredientDTO()
                )
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Double getRandomDouble() {
        return faker.number().randomDouble(2, 1, 100);
    }

    private Integer getRandomInteger() {
        return faker.number().numberBetween(1, 100);
    }
}
