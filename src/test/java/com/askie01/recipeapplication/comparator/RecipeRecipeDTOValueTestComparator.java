package com.askie01.recipeapplication.comparator;

import com.askie01.recipeapplication.dto.CategoryDTO;
import com.askie01.recipeapplication.dto.DifficultyDTO;
import com.askie01.recipeapplication.dto.IngredientDTO;
import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.model.entity.Category;
import com.askie01.recipeapplication.model.entity.Ingredient;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.model.entity.value.Difficulty;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class RecipeRecipeDTOValueTestComparator implements RecipeRecipeDTOTestComparator {

    private final LongIdTestComparator longIdTestComparator;
    private final ImageTestComparator imageTestComparator;
    private final StringNameTestComparator stringNameTestComparator;
    private final DescriptionTestComparator descriptionTestComparator;
    private final DifficultyDifficultyDTOTestComparator difficultyDifficultyDTOTestComparator;
    private final CategoryCategoryDTOTestComparator categoryCategoryDTOTestComparator;
    private final IngredientIngredientDTOTestComparator ingredientIngredientDTOTestComparator;
    private final ServingsTestComparator servingsTestComparator;
    private final CookingTimeTestComparator cookingTimeTestComparator;
    private final InstructionsTestComparator instructionsTestComparator;
    private final LongVersionTestComparator longVersionTestComparator;

    @Override
    public boolean compare(Recipe recipe, RecipeDTO recipeDTO) {
        final boolean haveEqualId = haveEqualId(recipe, recipeDTO);
        final boolean haveEqualImage = haveEqualImage(recipe, recipeDTO);
        final boolean haveEqualName = haveEqualName(recipe, recipeDTO);
        final boolean haveEqualDescription = haveEqualDescription(recipe, recipeDTO);
        final boolean haveEqualDifficulty = haveEqualDifficulty(recipe, recipeDTO);
        final boolean haveEqualCategories = haveEqualCategories(recipe, recipeDTO);
        final boolean haveEqualIngredients = haveEqualIngredients(recipe, recipeDTO);
        final boolean haveEqualServings = haveEqualServings(recipe, recipeDTO);
        final boolean haveEqualCookingTime = haveEqualCookingTime(recipe, recipeDTO);
        final boolean haveEqualInstructions = haveEqualInstructions(recipe, recipeDTO);
        final boolean haveEqualVersion = haveEqualVersion(recipe, recipeDTO);
        return haveEqualId &&
                haveEqualImage &&
                haveEqualName &&
                haveEqualDescription &&
                haveEqualDifficulty &&
                haveEqualCategories &&
                haveEqualIngredients &&
                haveEqualServings &&
                haveEqualCookingTime &&
                haveEqualInstructions &&
                haveEqualVersion;
    }

    private boolean haveEqualId(Recipe recipe, RecipeDTO recipeDTO) {
        return longIdTestComparator.compare(recipe, recipeDTO);
    }

    private boolean haveEqualImage(Recipe recipe, RecipeDTO recipeDTO) {
        return imageTestComparator.compare(recipe, recipeDTO);
    }

    private boolean haveEqualName(Recipe recipe, RecipeDTO recipeDTO) {
        return stringNameTestComparator.compare(recipe, recipeDTO);
    }

    private boolean haveEqualDescription(Recipe recipe, RecipeDTO recipeDTO) {
        return descriptionTestComparator.compare(recipe, recipeDTO);
    }

    private boolean haveEqualDifficulty(Recipe recipe, RecipeDTO recipeDTO) {
        final Difficulty difficulty = recipe.getDifficulty();
        final DifficultyDTO difficultyDTO = recipeDTO.getDifficultyDTO();
        return difficultyDifficultyDTOTestComparator.compare(difficulty, difficultyDTO);
    }

    private boolean haveEqualCategories(Recipe recipe, RecipeDTO recipeDTO) {
        final List<Category> categories = recipe.getCategories();
        final List<CategoryDTO> categoryDTOs = recipeDTO.getCategoryDTOs();
        final boolean haveEqualSize = categories.size() == categoryDTOs.size();

        if (haveEqualSize) {
            final List<Category> sortedCategories = sortCategoriesByName(categories);
            final List<CategoryDTO> sortedCategoryDTOs = sortCategoryDTOsByName(categoryDTOs);
            return areEqualCategories(sortedCategories, sortedCategoryDTOs);
        }
        return false;
    }

    private List<Category> sortCategoriesByName(List<Category> categories) {
        return categories.stream()
                .sorted(Comparator.comparing(Category::getName))
                .collect(java.util.stream.Collectors.toList());
    }

    private List<CategoryDTO> sortCategoryDTOsByName(List<CategoryDTO> categoryDTOs) {
        return categoryDTOs.stream()
                .sorted(Comparator.comparing(CategoryDTO::getName))
                .toList();
    }

    private boolean areEqualCategories(List<Category> categories, List<CategoryDTO> categoryDTOs) {
        for (int index = 0; index < categories.size(); index++) {
            final Category category = categories.get(index);
            final CategoryDTO categoryDTO = categoryDTOs.get(index);
            final boolean differentCategories = !categoryCategoryDTOTestComparator.compare(category, categoryDTO);
            if (differentCategories) {
                return false;
            }
        }
        return true;
    }

    private boolean haveEqualIngredients(Recipe recipe, RecipeDTO recipeDTO) {
        final List<Ingredient> ingredients = recipe.getIngredients();
        final List<IngredientDTO> ingredientDTOs = recipeDTO.getIngredientDTOs();
        final boolean haveEqualSize = ingredients.size() == ingredientDTOs.size();

        if (haveEqualSize) {
            final List<Ingredient> sortedIngredients = sortIngredientsByName(ingredients);
            final List<IngredientDTO> sortedIngredientDTOs = sortIngredientDTOsByName(ingredientDTOs);
            return areEqualIngredients(sortedIngredients, sortedIngredientDTOs);
        }
        return false;
    }

    private List<Ingredient> sortIngredientsByName(List<Ingredient> ingredients) {
        return ingredients.stream()
                .sorted(Comparator.comparing(Ingredient::getName))
                .toList();
    }

    private List<IngredientDTO> sortIngredientDTOsByName(List<IngredientDTO> ingredientDTOs) {
        return ingredientDTOs.stream()
                .sorted(Comparator.comparing(IngredientDTO::getName))
                .toList();
    }

    private boolean areEqualIngredients(List<Ingredient> ingredients, List<IngredientDTO> ingredientDTOs) {
        for (int index = 0; index < ingredients.size(); index++) {
            final Ingredient ingredient = ingredients.get(index);
            final IngredientDTO ingredientDTO = ingredientDTOs.get(index);
            final boolean differentIngredients = !ingredientIngredientDTOTestComparator.compare(ingredient, ingredientDTO);
            if (differentIngredients) {
                return false;
            }
        }
        return true;
    }

    private boolean haveEqualServings(Recipe recipe, RecipeDTO recipeDTO) {
        return servingsTestComparator.compare(recipe, recipeDTO);
    }

    private boolean haveEqualCookingTime(Recipe recipe, RecipeDTO recipeDTO) {
        return cookingTimeTestComparator.compare(recipe, recipeDTO);
    }

    private boolean haveEqualInstructions(Recipe recipe, RecipeDTO recipeDTO) {
        return instructionsTestComparator.compare(recipe, recipeDTO);
    }

    private boolean haveEqualVersion(Recipe recipe, RecipeDTO recipeDTO) {
        return longVersionTestComparator.compare(recipe, recipeDTO);
    }
}
