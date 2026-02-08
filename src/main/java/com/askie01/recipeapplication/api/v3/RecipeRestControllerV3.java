package com.askie01.recipeapplication.api.v3;

import com.askie01.recipeapplication.dto.RecipeRequestBody;
import com.askie01.recipeapplication.dto.UserRecipeResponseBody;
import com.askie01.recipeapplication.mapper.RecipeToUserRecipeResponseBodyMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.service.RecipeServiceV3;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v3/recipes")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.recipe.v3.enabled", havingValue = "true", matchIfMissing = true)
public class RecipeRestControllerV3 {

    private final RecipeServiceV3 service;
    private final RecipeToUserRecipeResponseBodyMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createRecipe(Authentication authentication,
                                             @Valid @RequestBody RecipeRequestBody requestBody) {
        final String username = authentication.getName();
        service.createRecipe(username, requestBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRecipeResponseBody> getRecipe(@PathVariable Long id) {
        final Recipe recipe = service.getRecipe(id);
        final UserRecipeResponseBody responseBody = mapper.mapToDTO(recipe);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserRecipeResponseBody>> getRecipes(@RequestParam String username,
                                                                   @RequestParam Integer pageNumber,
                                                                   @RequestParam Integer pageSize) {
        final List<UserRecipeResponseBody> responseBody = service.getRecipes(username, pageNumber, pageSize)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserRecipeResponseBody>> searchRecipes(@RequestParam String query,
                                                                      @RequestParam Integer pageNumber,
                                                                      @RequestParam Integer pageSize) {
        final List<UserRecipeResponseBody> responseBody = service.searchRecipes(query, pageNumber, pageSize)
                .stream()
                .map(mapper::mapToDTO)
                .toList();
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(Authentication authentication,
                                             @PathVariable Long id,
                                             @Valid @RequestBody RecipeRequestBody requestBody) {
        final String username = authentication.getName();
        service.updateRecipe(username, id, requestBody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(Authentication authentication,
                                             @PathVariable Long id) {
        final String username = authentication.getName();
        service.deleteRecipe(username, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
