package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasInstructionsTestBuilder;
import com.askie01.recipeapplication.configuration.InstructionsMapperConfiguration;
import com.askie01.recipeapplication.configuration.InstructionsValidatorConfiguration;
import com.askie01.recipeapplication.mapper.InstructionsMapper;
import com.askie01.recipeapplication.model.value.HasInstructions;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = InstructionsMapperConfiguration.class)
@Import(value = InstructionsValidatorConfiguration.class)
@TestPropertySource(properties = {
        "component.mapper.instructions-type=validated-instructions",
        "component.validator.instructions-type=non-blank-instructions"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("ValidatedInstructionsMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class ValidatedInstructionsMapperIntegrationTest {

    private HasInstructions source;
    private HasInstructions target;
    private final InstructionsMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = HasInstructionsTestBuilder.builder()
                .instructions("Source instructions")
                .build();
        this.target = HasInstructionsTestBuilder.builder()
                .instructions("Target instructions")
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
        mapper.map(source, target);
        final String sourceInstructions = source.getInstructions();
        final String targetInstructions = target.getInstructions();
        assertNotEquals(sourceInstructions, targetInstructions);
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