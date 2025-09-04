package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.factory.CategoryTestFactory;
import com.askie01.recipeapplication.factory.RandomCategoryTestFactory;
import com.askie01.recipeapplication.model.entity.Category;
import com.github.javafaker.DateAndTime;
import com.github.javafaker.Faker;
import com.github.javafaker.FunnyName;
import com.github.javafaker.Number;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomCategoryTestFactory unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomCategoryTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private Number fakerNumber;

    @Mock
    private FunnyName fakerFunnyName;

    @Mock
    private DateAndTime fakerDateAndTime;

    @Mock
    private Date fakerBirthday;
    private CategoryTestFactory factory;

    @BeforeEach
    void setUp() {
        this.factory = new RandomCategoryTestFactory(faker);
    }

    @Test
    @DisplayName("createCategory method should return random Category object")
    void createCategory_shouldReturnRandomCategory() {
        when(faker.number()).thenReturn(fakerNumber);
        when(fakerNumber.randomNumber(anyInt(), eq(false))).thenReturn(0L);
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Simple category name");
        when(faker.date()).thenReturn(fakerDateAndTime);
        when(fakerDateAndTime.birthday()).thenReturn(fakerBirthday);
        when(fakerBirthday.toInstant()).thenReturn(new Date().toInstant());

        final Category category = factory.createCategory();
        final Long categoryId = category.getId();
        final String categoryName = category.getName();
        final LocalDateTime categoryCreatedAt = category.getCreatedAt();
        final String categoryCreatedBy = category.getCreatedBy();
        final LocalDateTime categoryUpdatedAt = category.getUpdatedAt();
        final String categoryUpdatedBy = category.getUpdatedBy();
        final Long categoryVersion = category.getVersion();

        assertNotNull(category);
        assertNotNull(categoryId);
        assertNotNull(categoryName);
        assertNotNull(categoryCreatedAt);
        assertNotNull(categoryCreatedBy);
        assertNotNull(categoryUpdatedAt);
        assertNotNull(categoryUpdatedBy);
        assertNotNull(categoryVersion);
    }
}