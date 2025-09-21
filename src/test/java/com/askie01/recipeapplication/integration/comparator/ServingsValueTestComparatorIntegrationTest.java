package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.builder.HasServingsTestBuilder;
import com.askie01.recipeapplication.comparator.ServingsTestComparator;
import com.askie01.recipeapplication.configuration.ServingsValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.model.value.HasServings;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ServingsValueTestComparatorTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("ServingsValueTestComparator integration tests")
class ServingsValueTestComparatorIntegrationTest {

    private final ServingsTestComparator comparator;
    private HasServings source;
    private HasServings target;

    @BeforeEach
    void setUp() {
        this.source = HasServingsTestBuilder.builder()
                .servings(1.0)
                .build();
        this.target = HasServingsTestBuilder.builder()
                .servings(2.0)
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source servings is equal to target servings")
    void compare_whenSourceServingsIsEqualToTargetServings_returnsTrue() {
        final double sourceServings = source.getServings();
        target.setServings(sourceServings);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source servings is not equal to target servings")
    void compare_whenSourceServingsIsNotEqualToTargetServings_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source servings is null")
    void compare_whenSourceServingsIsNull_returnsFalse() {
        source.setServings(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target servings is null")
    void compare_whenTargetServingsIsNull_returnsFalse() {
        target.setServings(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when source is null")
    void compare_whenSourceIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(null, target));
    }

    @Test
    @DisplayName("compare method should throw NullPointerException when target is null")
    void compare_whenTargetIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> comparator.compare(source, null));
    }
}