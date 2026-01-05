package com.askie01.recipeapplication.unit.checker;

import com.askie01.recipeapplication.checker.*;
import com.askie01.recipeapplication.factory.*;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.value.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultRecipeTestPersistenceChecker unit tests")
@EnabledIfSystemProperty(named = "test.type", matches = "unit")
class DefaultRecipeTestPersistenceCheckerUnitTest {

    private Recipe source;
    private RecipeTestPersistenceChecker checker;

    @Mock
    private LongIdTestPresenceChecker longIdTestPresenceChecker;

    @Mock
    private ImageTestPresenceChecker imageTestPresenceChecker;

    @Mock
    private StringNameTestPresenceChecker stringNameTestPresenceChecker;

    @Mock
    private DescriptionTestPresenceChecker descriptionTestPresenceChecker;

    @Mock
    private RecipeDifficultyTestPresenceChecker recipeDifficultyTestPresenceChecker;

    @Mock
    private CategoryTestPersistenceChecker categoryTestPersistenceChecker;

    @Mock
    private IngredientTestPersistenceChecker ingredientTestPersistenceChecker;

    @Mock
    private ServingsTestPresenceChecker servingsTestPresenceChecker;

    @Mock
    private CookingTimeTestPresenceChecker cookingTimeTestPresenceChecker;

    @Mock
    private InstructionsTestPresenceChecker instructionsTestPresenceChecker;

    @Mock
    private SimpleAuditTestPresenceChecker simpleAuditTestPresenceChecker;

    @Mock
    private LongVersionTestPresenceChecker longVersionTestPresenceChecker;

    @BeforeEach
    void setUp() {
        this.checker = new DefaultRecipeTestPersistenceChecker(
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
        final Faker faker = Faker.instance();
        final DifficultyTestFactory difficultyTestFactory = new RandomDifficultyTestFactory(faker);
        final CategoryTestFactory categoryTestFactory = new RandomCategoryTestFactory(faker);
        final RandomMeasureUnitTestFactory measureUnitTestFactory = new RandomMeasureUnitTestFactory(faker);
        final IngredientTestFactory ingredientTestFactory = new RandomIngredientTestFactory(faker, measureUnitTestFactory);
        this.source = new RandomRecipeTestFactory(
                faker,
                difficultyTestFactory,
                categoryTestFactory,
                ingredientTestFactory
        ).createRecipe();
    }

    @Test
    @DisplayName("wasCreated method should return true when source has updatedAt and updatedBy fields are null.")
    void wasCreated_whenSourceHasUpdatedAtAndUpdatedByNull_returnsTrue() {
        source.setUpdatedAt(null);
        source.setUpdatedBy(null);
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(imageTestPresenceChecker.hasImage(any(HasImage.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(descriptionTestPresenceChecker.hasDescription(any(HasDescription.class))).thenReturn(true);
        when(recipeDifficultyTestPresenceChecker.hasDifficulty(any(Recipe.class))).thenReturn(true);
        when(categoryTestPersistenceChecker.wasCreated(any(Category.class))).thenReturn(true);
        when(ingredientTestPersistenceChecker.wasCreated(any(Ingredient.class))).thenReturn(true);
        when(servingsTestPresenceChecker.hasServings(any(HasServings.class))).thenReturn(true);
        when(cookingTimeTestPresenceChecker.hasCookingTime(any(HasCookingTime.class))).thenReturn(true);
        when(instructionsTestPresenceChecker.hasInstructions(any(HasInstructions.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(false);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(false);
        when(longVersionTestPresenceChecker.hasVersion(any(HasLongVersion.class))).thenReturn(true);
        final boolean result = checker.wasCreated(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("wasCreated method should return false when source has updatedAt and updatedBy fields are not null.")
    void wasCreated_whenSourceHasUpdatedAtAndUpdatedByNotNull_returnsFalse() {
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(imageTestPresenceChecker.hasImage(any(HasImage.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(descriptionTestPresenceChecker.hasDescription(any(HasDescription.class))).thenReturn(true);
        when(recipeDifficultyTestPresenceChecker.hasDifficulty(any(Recipe.class))).thenReturn(true);
        when(categoryTestPersistenceChecker.wasCreated(any(Category.class))).thenReturn(true);
        when(ingredientTestPersistenceChecker.wasCreated(any(Ingredient.class))).thenReturn(true);
        when(servingsTestPresenceChecker.hasServings(any(HasServings.class))).thenReturn(true);
        when(cookingTimeTestPresenceChecker.hasCookingTime(any(HasCookingTime.class))).thenReturn(true);
        when(instructionsTestPresenceChecker.hasInstructions(any(HasInstructions.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(longVersionTestPresenceChecker.hasVersion(any(HasLongVersion.class))).thenReturn(true);
        final boolean result = checker.wasCreated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasCreated method should return false when source categories were not created.")
    void wasCreated_whenSourceCategoriesWereNotCreated_returnsFalse() {
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(imageTestPresenceChecker.hasImage(any(HasImage.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(descriptionTestPresenceChecker.hasDescription(any(HasDescription.class))).thenReturn(true);
        when(recipeDifficultyTestPresenceChecker.hasDifficulty(any(Recipe.class))).thenReturn(true);
        when(categoryTestPersistenceChecker.wasCreated(any(Category.class))).thenReturn(false);
        when(ingredientTestPersistenceChecker.wasCreated(any(Ingredient.class))).thenReturn(true);
        when(servingsTestPresenceChecker.hasServings(any(HasServings.class))).thenReturn(true);
        when(cookingTimeTestPresenceChecker.hasCookingTime(any(HasCookingTime.class))).thenReturn(true);
        when(instructionsTestPresenceChecker.hasInstructions(any(HasInstructions.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(longVersionTestPresenceChecker.hasVersion(any(HasLongVersion.class))).thenReturn(true);
        final boolean result = checker.wasCreated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasCreated method should return false when source ingredients were not created.")
    void wasCreated_whenSourceIngredientsWereNotCreated_returnsFalse() {
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(imageTestPresenceChecker.hasImage(any(HasImage.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(descriptionTestPresenceChecker.hasDescription(any(HasDescription.class))).thenReturn(true);
        when(recipeDifficultyTestPresenceChecker.hasDifficulty(any(Recipe.class))).thenReturn(true);
        when(categoryTestPersistenceChecker.wasCreated(any(Category.class))).thenReturn(true);
        when(ingredientTestPersistenceChecker.wasCreated(any(Ingredient.class))).thenReturn(false);
        when(servingsTestPresenceChecker.hasServings(any(HasServings.class))).thenReturn(true);
        when(cookingTimeTestPresenceChecker.hasCookingTime(any(HasCookingTime.class))).thenReturn(true);
        when(instructionsTestPresenceChecker.hasInstructions(any(HasInstructions.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(longVersionTestPresenceChecker.hasVersion(any(HasLongVersion.class))).thenReturn(true);
        final boolean result = checker.wasCreated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasCreated method should throw NullPointerException when source is null.")
    void wasCreated_whenSourceIsNull_throwsNullPointerException() {
        when(longIdTestPresenceChecker.hasId(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> checker.wasCreated(null));
    }

    @Test
    @DisplayName("wasUpdated method should return true when source has updatedAt and updatedBy fields are not null.")
    void wasUpdated_whenSourceHasUpdatedAtAndUpdatedByNotNull_returnsTrue() {
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(imageTestPresenceChecker.hasImage(any(HasImage.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(descriptionTestPresenceChecker.hasDescription(any(HasDescription.class))).thenReturn(true);
        when(recipeDifficultyTestPresenceChecker.hasDifficulty(any(Recipe.class))).thenReturn(true);
        when(categoryTestPersistenceChecker.wasCreated(any(Category.class))).thenReturn(true);
        when(ingredientTestPersistenceChecker.wasCreated(any(Ingredient.class))).thenReturn(true);
        when(servingsTestPresenceChecker.hasServings(any(HasServings.class))).thenReturn(true);
        when(cookingTimeTestPresenceChecker.hasCookingTime(any(HasCookingTime.class))).thenReturn(true);
        when(instructionsTestPresenceChecker.hasInstructions(any(HasInstructions.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(longVersionTestPresenceChecker.hasVersion(any(HasLongVersion.class))).thenReturn(true);
        final boolean result = checker.wasUpdated(source);
        assertTrue(result);
    }

    @Test
    @DisplayName("wasUpdated method should return false when source has updatedAt and updatedBy fields are null.")
    void wasUpdated_whenSourceHasUpdatedAtAndUpdatedByNull_returnsFalse() {
        source.setUpdatedAt(null);
        source.setUpdatedBy(null);
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(imageTestPresenceChecker.hasImage(any(HasImage.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(descriptionTestPresenceChecker.hasDescription(any(HasDescription.class))).thenReturn(true);
        when(recipeDifficultyTestPresenceChecker.hasDifficulty(any(Recipe.class))).thenReturn(true);
        when(categoryTestPersistenceChecker.wasCreated(any(Category.class))).thenReturn(true);
        when(ingredientTestPersistenceChecker.wasCreated(any(Ingredient.class))).thenReturn(true);
        when(servingsTestPresenceChecker.hasServings(any(HasServings.class))).thenReturn(true);
        when(cookingTimeTestPresenceChecker.hasCookingTime(any(HasCookingTime.class))).thenReturn(true);
        when(instructionsTestPresenceChecker.hasInstructions(any(HasInstructions.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(false);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(false);
        when(longVersionTestPresenceChecker.hasVersion(any(HasLongVersion.class))).thenReturn(true);
        final boolean result = checker.wasUpdated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasUpdated method should return false when source categories were not created.")
    void wasUpdated_whenSourceCategoriesWereNotCreated_returnsFalse() {
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(imageTestPresenceChecker.hasImage(any(HasImage.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(descriptionTestPresenceChecker.hasDescription(any(HasDescription.class))).thenReturn(true);
        when(recipeDifficultyTestPresenceChecker.hasDifficulty(any(Recipe.class))).thenReturn(true);
        when(categoryTestPersistenceChecker.wasCreated(any(Category.class))).thenReturn(false);
        when(ingredientTestPersistenceChecker.wasCreated(any(Ingredient.class))).thenReturn(true);
        when(servingsTestPresenceChecker.hasServings(any(HasServings.class))).thenReturn(true);
        when(cookingTimeTestPresenceChecker.hasCookingTime(any(HasCookingTime.class))).thenReturn(true);
        when(instructionsTestPresenceChecker.hasInstructions(any(HasInstructions.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(longVersionTestPresenceChecker.hasVersion(any(HasLongVersion.class))).thenReturn(true);
        final boolean result = checker.wasUpdated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasUpdated method should return false when source ingredients were not created.")
    void wasUpdated_whenSourceIngredientsWereNotCreated_returnsFalse() {
        when(longIdTestPresenceChecker.hasId(any(HasLongId.class))).thenReturn(true);
        when(imageTestPresenceChecker.hasImage(any(HasImage.class))).thenReturn(true);
        when(stringNameTestPresenceChecker.hasName(any(HasStringName.class))).thenReturn(true);
        when(descriptionTestPresenceChecker.hasDescription(any(HasDescription.class))).thenReturn(true);
        when(recipeDifficultyTestPresenceChecker.hasDifficulty(any(Recipe.class))).thenReturn(true);
        when(categoryTestPersistenceChecker.wasCreated(any(Category.class))).thenReturn(true);
        when(ingredientTestPersistenceChecker.wasCreated(any(Ingredient.class))).thenReturn(false);
        when(servingsTestPresenceChecker.hasServings(any(HasServings.class))).thenReturn(true);
        when(cookingTimeTestPresenceChecker.hasCookingTime(any(HasCookingTime.class))).thenReturn(true);
        when(instructionsTestPresenceChecker.hasInstructions(any(HasInstructions.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasCreatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedAt(any(HasSimpleAudit.class))).thenReturn(true);
        when(simpleAuditTestPresenceChecker.hasUpdatedBy(any(HasSimpleAudit.class))).thenReturn(true);
        when(longVersionTestPresenceChecker.hasVersion(any(HasLongVersion.class))).thenReturn(true);
        final boolean result = checker.wasUpdated(source);
        assertFalse(result);
    }

    @Test
    @DisplayName("wasUpdated method should throw NullPointerException when source is null.")
    void wasUpdated_whenSourceIsNull_throwsNullPointerException() {
        when(longIdTestPresenceChecker.hasId(null)).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> checker.wasUpdated(null));
    }
}