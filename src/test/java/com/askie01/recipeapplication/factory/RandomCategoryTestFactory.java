package com.askie01.recipeapplication.factory;

import com.askie01.recipeapplication.model.entity.Category;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
public class RandomCategoryTestFactory implements CategoryTestFactory {

    private final Faker faker;

    public Category createCategory() {
        final Long randomId = getRandomLong();
        final String randomName = getRandomName();
        final LocalDateTime randomCreatedAt = getRandomLocalDateTime();
        final String randomCreatedBy = getRandomName();
        final LocalDateTime randomUpdatedAt = getRandomLocalDateTime();
        final String randomUpdatedBy = getRandomName();
        final Long randomVersion = getRandomLong();
        return Category.builder()
                .id(randomId)
                .name(randomName)
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

    private LocalDateTime getRandomLocalDateTime() {
        final Instant randomInstant = faker.date().birthday().toInstant();
        final ZoneId systemZoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(randomInstant, systemZoneId);
    }
}
