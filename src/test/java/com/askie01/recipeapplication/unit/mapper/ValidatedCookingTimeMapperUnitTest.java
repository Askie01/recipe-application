package com.askie01.recipeapplication.unit.mapper;

import com.askie01.recipeapplication.builder.TestHasCookingTimeBuilder;
import com.askie01.recipeapplication.mapper.CookingTimeMapper;
import com.askie01.recipeapplication.mapper.ValidatedCookingTimeMapper;
import com.askie01.recipeapplication.model.value.HasCookingTime;
import com.askie01.recipeapplication.validator.CookingTimeValidator;
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
@DisplayName("ValidatedCookingTimeMapper unit tests")
class ValidatedCookingTimeMapperUnitTest {

    @Mock
    private CookingTimeValidator validator;
    private CookingTimeMapper mapper;
    private HasCookingTime source;
    private HasCookingTime target;

    @BeforeEach
    void setUp() {
        this.mapper = new ValidatedCookingTimeMapper(validator);
        this.source = TestHasCookingTimeBuilder.builder()
                .cookingTime(10)
                .build();
        this.target = TestHasCookingTimeBuilder.builder()
                .cookingTime(20)
                .build();
    }

    @Test
    @DisplayName("map method should map source cooking time to target cooking time when source is valid")
    void map_whenSourceIsValid_mapsSourceCookingTimeToTargetCookingTime() {
        when(validator.isValid(source)).thenReturn(true);
        mapper.map(source, target);
        final Integer sourceCookingTime = source.getCookingTime();
        final Integer targetCookingTime = target.getCookingTime();
        assertEquals(sourceCookingTime, targetCookingTime);
    }

    @Test
    @DisplayName("map method should not map source cooking time to target cooking time when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceCookingTimeToTargetCookingTime() {
        when(validator.isValid(source)).thenReturn(false);
        final Integer targetCookingTimeBeforeMapping = target.getCookingTime();
        mapper.map(source, target);
        final Integer targetCookingTimeAfterMapping = target.getCookingTime();
        assertEquals(targetCookingTimeBeforeMapping, targetCookingTimeAfterMapping);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source cooking time is null")
    void map_whenSourceCookingTimeIsNull_throwsNullPointerException() {
        source.setCookingTime(null);
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