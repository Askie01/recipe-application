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
public class RandomRecipeDTOUnsavedEntityTestFactory implements RecipeDTOUnsavedEntityTestFactory {

    private final Faker faker;
    private final DifficultyDTOTestFactory difficultyDTOTestFactory;
    private final CategoryDTOUnsavedEntityTestFactory categoryDTOUnsavedEntityTestFactory;
    private final IngredientDTOUnsavedEntityTestFactory ingredientDTOUnsavedEntityTestFactory;

    @Override
    public RecipeDTO createRecipeDTO() {
        final byte[] randomImage = getRandomImage();
        final String randomName = getRandomName();
        final String randomDescription = getRandomDescription();
        final DifficultyDTO randomDifficultyDTO = getRandomDifficultyDTO();
        final Set<CategoryDTO> randomCategoryDTOs = getRandomCategoryDTOs();
        final Set<IngredientDTO> randomIngredientDTOs = getRandomIngredientDTOs();
        final Double randomServings = getRandomServings();
        final Integer randomCookingTime = getRandomCookingTime();
        final String randomInstructions = getRandomInstructions();
        return RecipeDTO.builder()
                .image(randomImage)
                .name(randomName)
                .description(randomDescription)
                .difficultyDTO(randomDifficultyDTO)
                .categoryDTOs(randomCategoryDTOs)
                .ingredientDTOs(randomIngredientDTOs)
                .servings(randomServings)
                .cookingTime(randomCookingTime)
                .instructions(randomInstructions)
                .build();
    }

    private byte[] getRandomImage() {
        return faker.lorem().characters(100000).getBytes();
    }

    private String getRandomName() {
        return getRandomString();
    }

    private String getRandomDescription() {
        return getRandomString();
    }

    private DifficultyDTO getRandomDifficultyDTO() {
        return difficultyDTOTestFactory.createDifficultyDTO();
    }

    private Set<CategoryDTO> getRandomCategoryDTOs() {
        return Stream.of(
                categoryDTOUnsavedEntityTestFactory.createCategoryDTO(),
                categoryDTOUnsavedEntityTestFactory.createCategoryDTO(),
                categoryDTOUnsavedEntityTestFactory.createCategoryDTO(),
                categoryDTOUnsavedEntityTestFactory.createCategoryDTO(),
                categoryDTOUnsavedEntityTestFactory.createCategoryDTO()
        ).collect(Collectors.toCollection(HashSet::new));
    }

    private Set<IngredientDTO> getRandomIngredientDTOs() {
        return Stream.of(
                ingredientDTOUnsavedEntityTestFactory.createIngredientDTO(),
                ingredientDTOUnsavedEntityTestFactory.createIngredientDTO(),
                ingredientDTOUnsavedEntityTestFactory.createIngredientDTO(),
                ingredientDTOUnsavedEntityTestFactory.createIngredientDTO(),
                ingredientDTOUnsavedEntityTestFactory.createIngredientDTO()
        ).collect(Collectors.toCollection(HashSet::new));
    }

    private Double getRandomServings() {
        return faker.number().randomDouble(2, 1, 100);
    }

    private Integer getRandomCookingTime() {
        return faker.number().numberBetween(1, 100);
    }

    private String getRandomInstructions() {
        return getRandomString();
    }

    private String getRandomString() {
        return faker.funnyName().name();
    }
}
