package com.askie01.recipeapplication.configuration.template;

import com.askie01.recipeapplication.checker.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DefaultRecipeTestPersistenceCheckerTestConfigurationTemplate {

    @Bean
    public RecipeTestPersistenceChecker recipeTestPersistenceChecker(LongIdTestPresenceChecker longIdTestPresenceChecker,
                                                                     ImageTestPresenceChecker imageTestPresenceChecker,
                                                                     StringNameTestPresenceChecker stringNameTestPresenceChecker,
                                                                     DescriptionTestPresenceChecker descriptionTestPresenceChecker,
                                                                     RecipeDifficultyTestPresenceChecker recipeDifficultyTestPresenceChecker,
                                                                     CategoryTestPersistenceChecker categoryTestPersistenceChecker,
                                                                     IngredientTestPersistenceChecker ingredientTestPersistenceChecker,
                                                                     ServingsTestPresenceChecker servingsTestPresenceChecker,
                                                                     CookingTimeTestPresenceChecker cookingTimeTestPresenceChecker,
                                                                     InstructionsTestPresenceChecker instructionsTestPresenceChecker,
                                                                     SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker,
                                                                     LongVersionTestPresenceChecker longVersionTestPresenceChecker) {
        return new DefaultRecipeTestPersistenceChecker(
                longIdTestPresenceChecker,
                imageTestPresenceChecker,
                stringNameTestPresenceChecker,
                descriptionTestPresenceChecker,
                recipeDifficultyTestPresenceChecker,
                categoryTestPersistenceChecker,
                ingredientTestPersistenceChecker,
                servingsTestPresenceChecker,
                cookingTimeTestPresenceChecker,
                instructionsTestPresenceChecker,
                simpleAuditTestPresenceChecker,
                longVersionTestPresenceChecker
        );
    }
}
