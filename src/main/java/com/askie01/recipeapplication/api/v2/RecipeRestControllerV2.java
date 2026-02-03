package com.askie01.recipeapplication.api.v2;

import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.dto.RecipeResponseBody;
import com.askie01.recipeapplication.mapper.RecipeToRecipeResponseBodyMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.service.RecipeServiceV2;
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
@RequestMapping("/api/v2/recipes")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.recipe.v2.enabled", havingValue = "true")
public class RecipeRestControllerV2 {

    private final RecipeServiceV2 service;
    private final RecipeToRecipeResponseBodyMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createRecipe(@Valid @RequestBody RecipeRequestBody requestBody) {
        service.createRecipe(requestBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseBody> getRecipe(@PathVariable Long id) {
        final Recipe recipe = service.getRecipe(id);
        final RecipeResponseBody responseBody = mapper.mapToDTO(recipe);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RecipeResponseBody>> getRecipes(@RequestParam int pageNumber,
                                                               @RequestParam int pageSize) {
        final List<RecipeResponseBody> responseBody = service
                .getRecipes(pageNumber, pageSize)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeResponseBody>> searchRecipes(@RequestParam String text,
                                                                  @RequestParam int pageNumber,
                                                                  @RequestParam int pageSize) {
        final List<RecipeResponseBody> responseBody = service
                .searchRecipes(text, pageNumber, pageSize)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id,
                                             @Valid @RequestBody RecipeRequestBody requestBody) {
        service.updateRecipe(id, requestBody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        service.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
