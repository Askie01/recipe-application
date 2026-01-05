package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasInstructionsTestBuilder;
import com.askie01.recipeapplication.comparator.InstructionsTestComparator;
import com.askie01.recipeapplication.configuration.InstructionsValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedInstructionsMapperDefaultTestConfiguration;
import com.askie01.recipeapplication.mapper.InstructionsMapper;
import com.askie01.recipeapplication.model.value.HasInstructions;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ValidatedInstructionsMapperDefaultTestConfiguration.class,
        InstructionsValueTestComparatorTestConfiguration.class
})
@TestPropertySource(locations = "classpath:validated-instructions-mapper-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("ValidatedInstructionsMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class ValidatedInstructionsMapperIntegrationTest {

    private HasInstructions source;
    private HasInstructions target;
    private final InstructionsMapper mapper;
    private final InstructionsTestComparator comparator;

    @BeforeEach
    void setUp() {
        final Faker faker = new Faker();
        final String sourceInstructions = faker.lorem().paragraph();
        final String targetInstructions = faker.lorem().paragraph();
        this.source = HasInstructionsTestBuilder.builder()
                .instructions(sourceInstructions)
                .build();
        this.target = HasInstructionsTestBuilder.builder()
                .instructions(targetInstructions)
                .build();
    }

    @Test
    @DisplayName("map method should map source instructions to target instructions when source is valid")
    void map_whenSourceInstructionsIsValid_mapsSourceInstructionsToTargetInstructions() {
        mapper.map(source, target);
        final boolean equalInstructions = comparator.compare(source, target);
        assertTrue(equalInstructions);
    }

    @Test
    @DisplayName("map method should not map source instructions to target instructions when source is invalid")
    void map_whenSourceInstructionsIsInvalid_doesNotMapSourceInstructionsToTargetInstructions() {
        source.setInstructions("");
        mapper.map(source, target);
        final boolean equalInstructions = comparator.compare(source, target);
        assertFalse(equalInstructions);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source instructions are null")
    void map_whenSourceInstructionsIsNull_throwsNullPointerException() {
        source.setInstructions(null);
        assertThrows(NullPointerException.class, () -> mapper.map(source, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}