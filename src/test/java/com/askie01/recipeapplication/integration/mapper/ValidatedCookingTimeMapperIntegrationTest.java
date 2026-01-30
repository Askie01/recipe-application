package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasCookingTimeTestBuilder;
import com.askie01.recipeapplication.configuration.CookingTimeMapperConfiguration;
import com.askie01.recipeapplication.configuration.CookingTimeValidatorConfiguration;
import com.askie01.recipeapplication.mapper.CookingTimeMapper;
import com.askie01.recipeapplication.model.value.HasCookingTime;
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

@SpringJUnitConfig(classes = CookingTimeMapperConfiguration.class)
@Import(value = CookingTimeValidatorConfiguration.class)
@TestPropertySource(properties = {
        "component.mapper.cooking-time-type=validated-cooking-time",
        "component.validator.cooking-time-type=positive-cooking-time"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("ValidatedCookingTimeMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class ValidatedCookingTimeMapperIntegrationTest {

    private HasCookingTime source;
    private HasCookingTime target;
    private final CookingTimeMapper mapper;

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
        final int sourceCookingTime = source.getCookingTime();
        final int targetCookingTime = target.getCookingTime();
        assertEquals(sourceCookingTime, targetCookingTime);
    }

    @Test
    @DisplayName("map method should not map source cooking time to target cooking time when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceCookingTimeToTargetCookingTime() {
        source.setCookingTime(0);
        mapper.map(source, target);
        final int sourceCookingTime = source.getCookingTime();
        final int targetCookingTime = target.getCookingTime();
        assertNotEquals(sourceCookingTime, targetCookingTime);
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