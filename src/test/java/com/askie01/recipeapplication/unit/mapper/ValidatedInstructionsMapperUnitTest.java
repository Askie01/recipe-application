package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.HasInstructionsTestBuilder;
import com.askie01.recipeapplication.mapper.InstructionsMapper;
import com.askie01.recipeapplication.mapper.ValidatedInstructionsMapper;
import com.askie01.recipeapplication.model.value.HasInstructions;
import com.askie01.recipeapplication.validator.InstructionsValidator;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
@DisplayName("ValidatedInstructionsMapper unit tests")
class ValidatedInstructionsMapperUnitTest {

    @Mock
    private InstructionsValidator validator;
    private InstructionsMapper mapper;
    private HasInstructions source;
    private HasInstructions target;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedInstructionsMapper(validator);
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
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final String sourceInstructions = source.getInstructions();
        final String targetInstructions = target.getInstructions();
        assertEquals(sourceInstructions, targetInstructions);
    }

    @Test
    @DisplayName("map method should not map source instructions to target instructions when source is invalid")
    void map_whenSourceInstructionsIsInvalid_doesNotMapSourceInstructionsToTargetInstructions() {
        when(validator.isValid(source)).thenReturn(false);
        final String targetInstructionsBeforeMapping = target.getInstructions();
        mapper.map(source, target);
        final String targetInstructionsAfterMapping = target.getInstructions();
        assertEquals(targetInstructionsBeforeMapping, targetInstructionsAfterMapping);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source instructions are null")
    void map_whenSourceInstructionsIsNull_throwsNullPointerException() {
        source.setInstructions(null);
        when(validator.isValid(source)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> mapper.map(source, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source is null")
    void map_whenSourceIsNull_throwsNullPointerException() {
        when(validator.isValid(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> mapper.map(null, target));
    }

    @Test
    @DisplayName("map method should throw NullPointerException when target is null")
    void map_whenTargetIsNull_throwsNullPointerException() {
        when(validator.isValid(source)).thenReturn(true);
        assertThrows(NullPointerException.class, () -> mapper.map(source, null));
    }
}