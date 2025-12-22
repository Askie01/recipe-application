package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.RandomMeasureUnitTestFactoryDefaultTestConfiguration;
import com.askie01.recipeapplication.factory.MeasureUnitTestFactory;
import com.askie01.recipeapplication.model.entity.MeasureUnit;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RandomMeasureUnitTestFactoryDefaultTestConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("RandomMeasureUnitTestFactory integration tests")
class RandomMeasureUnitTestFactoryIntegrationTest {

    private final MeasureUnitTestFactory factory;

    @Test
    @DisplayName("createMeasureUnit method should return random MeasureUnit object")
    void createMeasureUnit_shouldReturnRandomMeasureUnit() {
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