package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.TestHasServingsBuilder;
import com.askie01.recipeapplication.configuration.PositiveServingsValidatorConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedServingsMapperConfiguration;
import com.askie01.recipeapplication.mapper.ServingsMapper;
import com.askie01.recipeapplication.model.value.HasServings;
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
        ValidatedServingsMapperConfiguration.class,
        PositiveServingsValidatorConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {
        "component.mapper.servings-type=validated-servings",
        "component.validator.servings-type=positive-servings"
})
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("ValidatedServingsMapper integration tests")
class ValidatedServingsMapperIntegrationTest {

    private final ServingsMapper mapper;
    private HasServings source;
    private HasServings target;

    @BeforeEach
    void setUp() {
        this.source = TestHasServingsBuilder.builder()
                .servings(1.0)
                .build();
        this.target = TestHasServingsBuilder.builder()
                .servings(5.0)
                .build();
    }

    @Test
    @DisplayName("map method should map source servings to target servings when source is valid")
    void map_whenSourceIsValid_mapsSourceServingsToTargetServings() {
        mapper.map(source, target);
        final Double sourceServings = source.getServings();
        final Double targetServings = target.getServings();
        assertEquals(sourceServings, targetServings);
    }

    @Test
    @DisplayName("map method should not map source servings to target servings when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceServingsToTargetServings() {
        source.setServings(-1.0);
        final Double targetServingsBeforeMapping = target.getServings();
        mapper.map(source, target);
        final Double targetServingsAfterMapping = target.getServings();
        assertEquals(targetServingsBeforeMapping, targetServingsAfterMapping);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source servings is null")
    void map_whenSourceServingsIsNull_throwsNullPointerException() {
        source.setServings(null);
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