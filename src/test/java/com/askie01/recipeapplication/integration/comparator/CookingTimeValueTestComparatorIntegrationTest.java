package com.askie01.recipeapplication.integration.comparator;

import com.askie01.recipeapplication.builder.HasCookingTimeTestBuilder;
import com.askie01.recipeapplication.comparator.CookingTimeTestComparator;
import com.askie01.recipeapplication.configuration.CookingTimeValueTestComparatorTestConfiguration;
import com.askie01.recipeapplication.model.value.HasCookingTime;
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
        CookingTimeValueTestComparatorTestConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("CookingTimeValueTestComparator integration tests")
class CookingTimeValueTestComparatorIntegrationTest {

    private final CookingTimeTestComparator comparator;
    private HasCookingTime source;
    private HasCookingTime target;

    @BeforeEach
    void setUp() {
        this.source = HasCookingTimeTestBuilder.builder()
                .cookingTime(30)
                .build();
        this.target = HasCookingTimeTestBuilder.builder()
                .cookingTime(60)
                .build();
    }

    @Test
    @DisplayName("compare method should return true when source cooking time is equal to target cooking time")
    void compare_whenSourceCookingTimeIsEqualToTargetCookingTime_returnsTrue() {
        final Integer sourceCookingTime = source.getCookingTime();
        target.setCookingTime(sourceCookingTime);
        final boolean result = comparator.compare(source, target);
        assertTrue(result);
    }

    @Test
    @DisplayName("compare method should return false when source cooking time is not equal to target cooking time")
    void compare_whenSourceCookingTimeIsNotEqualToTargetCookingTime_returnsFalse() {
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when source cooking time is null")
    void compare_whenSourceCookingTimeIsNull_returnsFalse() {
        source.setCookingTime(null);
        final boolean result = comparator.compare(source, target);
        assertFalse(result);
    }

    @Test
    @DisplayName("compare method should return false when target cooking time is null")
    void compare_whenTargetCookingTimeIsNull_returnsFalse() {
        target.setCookingTime(null);
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