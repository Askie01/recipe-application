package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasServingsTestBuilder;
import com.askie01.recipeapplication.comparator.ServingsTestComparator;
import com.askie01.recipeapplication.configuration.ServingsValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedServingsMapperDefaultTestConfiguration;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ValidatedServingsMapperDefaultTestConfiguration.class,
        ServingsValueTestComparatorTestConfiguration.class
})
@TestPropertySource(locations = "classpath:validated-servings-mapper-default-test-configuration.properties")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("ValidatedServingsMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class ValidatedServingsMapperIntegrationTest {

    private HasServings source;
    private HasServings target;
    private final ServingsMapper mapper;
    private final ServingsTestComparator comparator;

    @BeforeEach
    void setUp() {
        this.source = HasServingsTestBuilder.builder()
                .servings(1.0)
                .build();
        this.target = HasServingsTestBuilder.builder()
                .servings(5.0)
                .build();
    }

    @Test
    @DisplayName("map method should map source servings to target servings when source is valid")
    void map_whenSourceIsValid_mapsSourceServingsToTargetServings() {
        mapper.map(source, target);
        final boolean equalServings = comparator.compare(source, target);
        assertTrue(equalServings);
    }

    @Test
    @DisplayName("map method should not map source servings to target servings when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceServingsToTargetServings() {
        source.setServings(-1.0);
        mapper.map(source, target);
        final boolean equalServings = comparator.compare(source, target);
        assertFalse(equalServings);
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