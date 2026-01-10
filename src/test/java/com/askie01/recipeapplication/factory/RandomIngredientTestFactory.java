package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
public class RandomIngredientTestFactory implements IngredientTestFactory {

    private final Faker faker;
    private final MeasureUnitTestFactory measureUnitTestFactory;

    public Ingredient createIngredient() {
        final Long randomId = getRandomLong();
        final String randomName = getRandomName();
        final Double randomAmount = getRandomDouble();
        final MeasureUnit randomMeasureUnit = getMeasureUnit();
        final LocalDateTime randomCreatedAt = getRandomLocalDateTime();
        final String randomCreatedBy = getRandomName();
        final LocalDateTime randomUpdatedAt = getRandomLocalDateTime();
        final String randomUpdatedBy = getRandomName();
        final Long randomVersion = getRandomLong();
        return Ingredient.builder()
                .id(randomId)
                .name(randomName)
                .amount(randomAmount)
                .measureUnit(randomMeasureUnit)
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

    private String getRandomName() {
        return faker.funnyName().name();
    }

    private double getRandomDouble() {
        return faker.number().randomDouble(2, 1, 100);
    }

    private MeasureUnit getMeasureUnit() {
        return measureUnitTestFactory.createMeasureUnit();
    }

    private LocalDateTime getRandomLocalDateTime() {
        final Instant randomInstant = faker.date().birthday().toInstant();
        final ZoneId systemZoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(randomInstant, systemZoneId);
    }
}
