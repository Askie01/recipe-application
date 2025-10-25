package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class RandomRecipeTestFactory implements RecipeTestFactory {

    private final Faker faker;
    private final DifficultyTestFactory difficultyTestFactory;
    private final CategoryTestFactory categoryTestFactory;
    private final IngredientTestFactory ingredientTestFactory;

    @Override
    public Recipe createRecipe() {
        final Long randomId = getRandomLong();
        final byte[] randomImage = getRandomByteArray();
        final String randomName = getRandomString();
        final String randomDescription = getRandomString();
        final Difficulty randomDifficulty = getRandomDifficulty();
        final Set<Category> randomCategories = getRandomCategories();
        final Set<Ingredient> randomIngredients = getRandomIngredients();
        final Double randomServings = getRandomDouble();
        final Integer randomCookingTime = getRandomInteger();
        final String randomInstructions = getRandomString();
        final LocalDateTime randomCreatedAt = getRandomLocalDateTime();
        final String randomCreatedBy = getRandomString();
        final LocalDateTime randomUpdatedAt = getRandomLocalDateTime();
        final String randomUpdatedBy = getRandomString();
        final Long randomVersion = getRandomLong();
        return Recipe.builder()
                .id(randomId)
                .image(randomImage)
                .name(randomName)
                .description(randomDescription)
                .difficulty(randomDifficulty)
                .categories(randomCategories)
                .ingredients(randomIngredients)
                .servings(randomServings)
                .cookingTime(randomCookingTime)
                .instructions(randomInstructions)
                .createdAt(randomCreatedAt)
                .createdBy(randomCreatedBy)
                .updatedAt(randomUpdatedAt)
                .updatedBy(randomUpdatedBy)
                .version(randomVersion)
                .build();
    }

    private Long getRandomLong() {
        return faker.number().randomNumber(100000, false);
    }

    private byte[] getRandomByteArray() {
        return faker.lorem().characters(100000).getBytes();
    }

    private String getRandomString() {
        return faker.funnyName().name();
    }

    private Difficulty getRandomDifficulty() {
        return difficultyTestFactory.createDifficulty();
    }

    private Set<Category> getRandomCategories() {
        return Stream.of(
                        categoryTestFactory.createCategory(),
                        categoryTestFactory.createCategory(),
                        categoryTestFactory.createCategory(),
                        categoryTestFactory.createCategory(),
                        categoryTestFactory.createCategory()
                )
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Set<Ingredient> getRandomIngredients() {
        return Stream.of(
                        ingredientTestFactory.createIngredient(),
                        ingredientTestFactory.createIngredient(),
                        ingredientTestFactory.createIngredient(),
                        ingredientTestFactory.createIngredient(),
                        ingredientTestFactory.createIngredient()
                )
                .collect(Collectors.toCollection(HashSet::new));
    }

    private Double getRandomDouble() {
        return faker.number().randomDouble(2, 1, 100);
    }

    private Integer getRandomInteger() {
        return faker.number().numberBetween(1, 100);
    }

    private LocalDateTime getRandomLocalDateTime() {
        final Instant randomInstant = faker.date().birthday().toInstant();
        final ZoneId systemZoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(randomInstant, systemZoneId);
    }
}
