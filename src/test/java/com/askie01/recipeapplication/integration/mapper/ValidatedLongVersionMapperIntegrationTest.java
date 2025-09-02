package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasLongVersionTestBuilder;
import com.askie01.recipeapplication.configuration.PositiveLongVersionValidatorConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedLongVersionMapperConfiguration;
import com.askie01.recipeapplication.mapper.LongVersionMapper;
import com.askie01.recipeapplication.model.value.HasLongVersion;
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
        ValidatedLongVersionMapperConfiguration.class,
        PositiveLongVersionValidatorConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {
        "component.mapper.version-type=validated-long-version",
        "component.validator.version-type=positive-long-version"
})
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("ValidatedLongVersionMapper integration tests")
class ValidatedLongVersionMapperIntegrationTest {

    private final LongVersionMapper mapper;
    private HasLongVersion source;
    private HasLongVersion target;

    @BeforeEach
    void setUp() {
        this.source = HasLongVersionTestBuilder.builder()
                .version(1L)
                .build();
        this.target = HasLongVersionTestBuilder.builder()
                .version(5L)
                .build();
    }

    @Test
    @DisplayName("map method should map source version to target version when source is valid")
    void map_whenSourceIsValid_mapsSourceVersionToTargetVersion() {
        mapper.map(source, target);
        final Long sourceVersion = source.getVersion();
        final Long targetVersion = target.getVersion();
        assertEquals(sourceVersion, targetVersion);
    }

    @Test
    @DisplayName("map method should not map source version to target version when source is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceVersionToTargetVersion() {
        source.setVersion(-1L);
        final Long targetVersionBeforeMapping = target.getVersion();
        mapper.map(source, target);
        final Long targetVersionAfterMapping = target.getVersion();
        assertEquals(targetVersionBeforeMapping, targetVersionAfterMapping);
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