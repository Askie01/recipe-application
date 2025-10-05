package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasCookingTimeTestBuilder;
import com.askie01.recipeapplication.comparator.CookingTimeTestComparator;
import com.askie01.recipeapplication.configuration.CookingTimeValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.configuration.PositiveCookingTimeValidatorConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedCookingTimeMapperConfiguration;
import com.askie01.recipeapplication.mapper.CookingTimeMapper;
import com.askie01.recipeapplication.model.value.HasCookingTime;
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
        ValidatedCookingTimeMapperConfiguration.class,
        PositiveCookingTimeValidatorConfiguration.class,
        CookingTimeValueTestComparatorTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {
        "component.mapper.cooking-time-type=validated-cooking-time",
        "component.validator.cooking-time-type=positive-cooking-time"
})
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("ValidatedCookingTimeMapper integration tests")
class ValidatedCookingTimeMapperIntegrationTest {

    private final CookingTimeMapper mapper;
    private HasCookingTime source;
    private HasCookingTime target;

    private final CookingTimeTestComparator cookingTimeComparator;

    @BeforeEach
    void setUp() {
        this.source = HasCookingTimeTestBuilder.builder()
                .cookingTime(10)
                .build();
        this.target = HasCookingTimeTestBuilder.builder()
                .cookingTime(20)
                .build();
    }

    @Test
    @DisplayName("map method should map source cooking time to target cooking time when source is valid")
    void map_whenSourceIsValid_mapsSourceCookingTimeToTargetCookingTime() {
        mapper.map(source, target);
        final boolean equalCookingTime = cookingTimeComparator.compare(source, target);
        assertTrue(equalCookingTime);
    }

    @Test
    @DisplayName("map method should not map source cooking time to target cooking time when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceCookingTimeToTargetCookingTime() {
        source.setCookingTime(0);
        mapper.map(source, target);
        final boolean equalCookingTime = cookingTimeComparator.compare(source, target);
        assertFalse(equalCookingTime);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source cooking time is null")
    void map_whenSourceCookingTimeIsNull_throwsNullPointerException() {
        source.setCookingTime(null);
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