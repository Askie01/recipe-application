package com.askie01.recipeapplication.unit.factory;

import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.factory.DifficultyDTOTestFactory;
import com.askie01.recipeapplication.factory.RandomDifficultyDTOTestFactory;
import com.github.javafaker.Faker;
import com.github.javafaker.Number;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("RandomDifficultyDTOTestFactory unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class RandomDifficultyDTOTestFactoryUnitTest {

    @Mock
    private Faker faker;

    @Mock
    private Number fakerNumber;
    private DifficultyDTOTestFactory factory;

    @BeforeEach
    void setUp() {
        this.factory = new RandomDifficultyDTOTestFactory(faker);
    }

    @Test
    @DisplayName("createDifficultyDTO method should return random DifficultyDTO object")
    void createDifficultyDTO_shouldReturnRandomDifficultyDTO() {
        when(faker.number()).thenReturn(fakerNumber);
        when(fakerNumber.numberBetween(anyInt(), anyInt())).thenReturn(0);
        final DifficultyDTO difficultyDTO = factory.createDifficultyDTO();
        assertNotNull(difficultyDTO);
    }
}