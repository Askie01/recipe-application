package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.factory.MeasureUnitTestFactory;
import com.askie01.recipeapplication.factory.RandomMeasureUnitTestFactory;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
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
@DisplayName("RandomMeasureUnitTestFactory unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomMeasureUnitTestFactoryUnitTest {

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
    private MeasureUnitTestFactory factory;

    @BeforeEach
    void setUp() {
        this.factory = new RandomMeasureUnitTestFactory(faker);
    }

    @Test
    @DisplayName("createMeasureUnit method should return random MeasureUnit object")
    void createMeasureUnit_shouldReturnRandomMeasureUnit() {
        when(faker.number()).thenReturn(fakerNumber);
        when(fakerNumber.randomNumber(anyInt(), eq(false))).thenReturn(0L);
        when(faker.funnyName()).thenReturn(fakerFunnyName);
        when(fakerFunnyName.name()).thenReturn("Simple measure unit name");
        when(faker.date()).thenReturn(fakerDateAndTime);
        when(fakerDateAndTime.birthday()).thenReturn(fakerBirthday);
        when(fakerBirthday.toInstant()).thenReturn(new Date().toInstant());

        final MeasureUnit measureUnit = factory.createMeasureUnit();
        final Long measureUnitId = measureUnit.getId();
        final String measureUnitName = measureUnit.getName();
        final LocalDateTime measureUnitCreatedAt = measureUnit.getCreatedAt();
        final String measureUnitCreatedBy = measureUnit.getCreatedBy();
        final LocalDateTime measureUnitUpdatedAt = measureUnit.getUpdatedAt();
        final String measureUnitUpdatedBy = measureUnit.getUpdatedBy();
        final Long measureUnitVersion = measureUnit.getVersion();

        assertNotNull(measureUnit);
        assertNotNull(measureUnitId);
        assertNotNull(measureUnitName);
        assertNotNull(measureUnitCreatedAt);
        assertNotNull(measureUnitCreatedBy);
        assertNotNull(measureUnitUpdatedAt);
        assertNotNull(measureUnitUpdatedBy);
        assertNotNull(measureUnitVersion);
    }
}