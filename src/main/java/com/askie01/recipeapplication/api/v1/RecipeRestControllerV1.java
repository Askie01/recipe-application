package com.askie01.recipeapplication.api.v1;

import com.askie01.recipeapplication.dto.RecipeDTO;
import com.askie01.recipeapplication.mapper.RecipeToRecipeDTOMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.service.RecipeServiceV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/api/v1/recipes")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.recipe.v1.enabled", havingValue = "true")
public class RecipeRestControllerV1 {

    private final RecipeServiceV1 recipeService;
    private final RecipeToRecipeDTOMapper recipeToRecipeDTOMapper;

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        final Recipe recipe = recipeService.createRecipe(recipeDTO);
        final RecipeDTO newRecipeDTO = recipeToRecipeDTOMapper.mapToDTO(recipe);
        return new ResponseEntity<>(newRecipeDTO, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RecipeDTO> getRecipe(@PathVariable Long id) {
        final Recipe recipe = recipeService.getRecipe(id);
        final RecipeDTO recipeDTO = recipeToRecipeDTOMapper.mapToDTO(recipe);
        return new ResponseEntity<>(recipeDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getRecipes() {
        final List<RecipeDTO> recipeDTOs = recipeService
                .getRecipes()
                .stream()
                .map(recipeToRecipeDTOMapper::mapToDTO)
                .toList();
        return new ResponseEntity<>(recipeDTOs, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RecipeDTO> updateRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        final Recipe updatedRecipe = recipeService.updateRecipe(recipeDTO);
        final RecipeDTO updatedRecipeDTO = recipeToRecipeDTOMapper.mapToDTO(updatedRecipe);
        return new ResponseEntity<>(updatedRecipeDTO, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<RecipeDTO> deleteRecipe(@PathVariable Long id) {
        final Recipe deletedRecipe = recipeService.deleteRecipe(id);
        final RecipeDTO deletedRecipeDTO = recipeToRecipeDTOMapper.mapToDTO(deletedRecipe);
        return new ResponseEntity<>(deletedRecipeDTO, HttpStatus.OK);
    }
}
