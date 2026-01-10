package com.askie01.recipeapplication.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(value = {
        EnableJpaAuditingConfiguration.class,
        RecipeServiceStringAuditorConfiguration.class,
        DefaultRecipeServiceV1DefaultTestConfiguration.class,
        SimpleRecipeToRecipeDTOMapperDefaultTestConfiguration.class
})
public class RecipeRestControllerV1TestConfiguration {
}
