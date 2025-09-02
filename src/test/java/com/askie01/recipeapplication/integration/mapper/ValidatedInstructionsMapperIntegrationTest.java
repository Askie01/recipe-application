package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasInstructionsTestBuilder;
import com.askie01.recipeapplication.configuration.NonBlankInstructionsValidatorConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedInstructionsMapperConfiguration;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ValidatedInstructionsMapperConfiguration.class,
        NonBlankInstructionsValidatorConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {
        "component.mapper.instructions-type=validated-instructions",
        "component.validator.instructions-type=non-blank-instructions"
})
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("ValidatedInstructionsMapper integration tests")
class ValidatedInstructionsMapperIntegrationTest {

    private final InstructionsMapper mapper;
    private HasInstructions source;
    private HasInstructions target;

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
        final String sourceInstructions = source.getInstructions();
        final String targetInstructions = target.getInstructions();
        assertEquals(sourceInstructions, targetInstructions);
    }

    @Test
    @DisplayName("map method should not map source instructions to target instructions when source is invalid")
    void map_whenSourceInstructionsIsInvalid_doesNotMapSourceInstructionsToTargetInstructions() {
        source.setInstructions("");
        final String targetInstructionsBeforeMapping = target.getInstructions();
        mapper.map(source, target);
        final String targetInstructionsAfterMapping = target.getInstructions();
        assertEquals(targetInstructionsBeforeMapping, targetInstructionsAfterMapping);
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