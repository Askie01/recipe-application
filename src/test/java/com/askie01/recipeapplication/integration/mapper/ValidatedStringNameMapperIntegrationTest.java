package com.askie01.recipeapplication.integration.mapper;

import com.askie01.recipeapplication.builder.HasStringNameTestBuilder;
import com.askie01.recipeapplication.configuration.NonBlankStringNameValidatorConfiguration;
import com.askie01.recipeapplication.configuration.ValidatedStringNameMapperConfiguration;
import com.askie01.recipeapplication.mapper.StringNameMapper;
import com.askie01.recipeapplication.model.value.HasStringName;
import com.github.javafaker.Faker;
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
        ValidatedStringNameMapperConfiguration.class,
        NonBlankStringNameValidatorConfiguration.class
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestPropertySource(properties = {
        "component.mapper.name-type=validated-string-name",
        "component.validator.name-type=non-blank-string"
})
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("ValidatedStringNameMapper integration tests")
class ValidatedStringNameMapperIntegrationTest {

    private final StringNameMapper mapper;
    private HasStringName source;
    private HasStringName target;

    @BeforeEach
    void setUp() {
        final Faker faker = new Faker();
        this.source = HasStringNameTestBuilder.builder()
                .name(faker.funnyName().name())
                .build();
        this.target = HasStringNameTestBuilder.builder()
                .name(faker.funnyName().name())
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
        final String targetNameBeforeMapping = target.getName();
        mapper.map(source, target);
        final String targetNameAfterMapping = target.getName();
        assertEquals(targetNameBeforeMapping, targetNameAfterMapping);
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