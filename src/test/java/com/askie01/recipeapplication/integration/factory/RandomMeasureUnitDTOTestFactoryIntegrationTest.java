package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.RandomMeasureUnitDTOTestFactoryDefaultTestConfiguration;
import com.askie01.recipeapplication.dto.MeasureUnitDTO;
import com.askie01.recipeapplication.factory.MeasureUnitDTOTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RandomMeasureUnitDTOTestFactoryDefaultTestConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("RandomMeasureUnitDTOTestFactory integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class RandomMeasureUnitDTOTestFactoryIntegrationTest {

    private final MeasureUnitDTOTestFactory factory;

    @Test
    @DisplayName("createMeasureUnitDTO method should return random MeasureUnitDTO object")
    void createMeasureUnitDTO_shouldReturnRandomMeasureUnitDTO() {
        final MeasureUnitDTO measureUnitDTO = factory.createMeasureUnitDTO();
        final Long measureUnitId = measureUnitDTO.getId();
        final String measureUnitName = measureUnitDTO.getName();
        final Long measureUnitVersion = measureUnitDTO.getVersion();

        assertNotNull(measureUnitDTO);
        assertNotNull(measureUnitId);
        assertNotNull(measureUnitName);
        assertNotNull(measureUnitVersion);
    }
}