package com.askie01.recipeapplication.integration.factory;

import com.askie01.recipeapplication.configuration.RandomDifficultyDTOTestFactoryDefaultTestConfiguration;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.factory.DifficultyDTOTestFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RandomDifficultyDTOTestFactoryDefaultTestConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@EnabledIfSystemProperty(named = "test.type", matches = "integration")
@DisplayName("RandomDifficultyDTOTestFactory integration tests")
class RandomDifficultyDTOTestFactoryIntegrationTest {

    private final DifficultyDTOTestFactory factory;

    @Test
    @DisplayName("createDifficultyDTO method should return random DifficultyDTO object")
    void createDifficultyDTO_shouldReturnRandomDifficultyDTO() {
        final DifficultyDTO difficultyDTO = factory.createDifficultyDTO();
        assertNotNull(difficultyDTO);
    }
}