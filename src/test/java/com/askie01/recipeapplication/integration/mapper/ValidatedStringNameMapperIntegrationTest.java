package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasStringNameTestBuilder;
import com.askie01.recipeapplication.configuration.StringNameMapperConfiguration;
import com.askie01.recipeapplication.configuration.StringNameValidatorConfiguration;
import com.askie01.recipeapplication.mapper.StringNameMapper;
import com.askie01.recipeapplication.model.value.HasStringName;
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

@SpringJUnitConfig(classes = StringNameMapperConfiguration.class)
@Import(StringNameValidatorConfiguration.class)
@TestPropertySource(properties = {
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string"
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DisplayName("ValidatedStringNameMapper integration tests")
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
class ValidatedStringNameMapperIntegrationTest {

    private HasStringName source;
    private HasStringName target;
    private final StringNameMapper mapper;

    @BeforeEach
    void setUp() {
        this.source = HasStringNameTestBuilder.builder()
                .name("Source name")
                .build();
        this.target = HasStringNameTestBuilder.builder()
                .name("Target name")
                .build();
    }

    @Test
    @DisplayName("map method should map source name to target name when source name is valid")
    void map_whenSourceIsValid_mapsSourceNameToTargetName() {
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertEquals(sourceName, targetName);
    }

    @Test
    @DisplayName("map method should not map source name to target name when source name is invalid")
    void map_whenSourceIsInvalid_doesNotMapSourceNameToTargetName() {
        source.setName("");
        mapper.map(source, target);
        final String sourceName = source.getName();
        final String targetName = target.getName();
        assertNotEquals(sourceName, targetName);
    }

    @Test
    @DisplayName("map method should throw NullPointerException when source name is null")
    void map_whenSourceNameIsNull_throwsNullPointerException() {
        source.setName(null);
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