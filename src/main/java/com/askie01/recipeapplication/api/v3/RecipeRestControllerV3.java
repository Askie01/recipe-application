package com.askie01.recipeapplication.api.v3;

import com.askie01.recipeapplication.dto.CustomerRecipeRequestBody;
import com.askie01.recipeapplication.dto.CustomerRecipeResponseBody;
import com.askie01.recipeapplication.dto.PageResponse;
import com.askie01.recipeapplication.dto.SearchRecipeResponseBody;
import com.askie01.recipeapplication.mapper.RecipeToCustomerRecipeResponseBodyMapper;
import com.askie01.recipeapplication.mapper.RecipeToSearchRecipeResponseBodyMapper;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.service.RecipeServiceV3;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/api/v3/recipes")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.recipe.v3.enabled", havingValue = "true", matchIfMissing = true)
public class RecipeRestControllerV3 {

    private final RecipeServiceV3 service;
    private final RecipeToSearchRecipeResponseBodyMapper recipeToSearchRecipeMapper;
    private final RecipeToCustomerRecipeResponseBodyMapper recipeToCustomerRecipeMapper;

    @PostMapping
    public ResponseEntity<Void> createRecipe(Authentication authentication,
                                             @Valid @RequestBody CustomerRecipeRequestBody requestBody) {
        final String username = authentication.getName();
        service.createRecipe(username, requestBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SearchRecipeResponseBody> getRecipe(@PathVariable Long id) {
        final Recipe recipe = service.getRecipe(id);
        final SearchRecipeResponseBody responseBody = recipeToSearchRecipeMapper.mapToDTO(recipe);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getRecipeImage(@PathVariable Long id) {
        final byte[] recipeImage = service.getRecipeImage(id);
        return new ResponseEntity<>(recipeImage, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CustomerRecipeResponseBody>> getUserRecipes(@RequestParam(
                                                                                           required = false,
                                                                                           defaultValue = ""
                                                                                   ) String username,
                                                                                   Pageable pageable) {
        final Page<CustomerRecipeResponseBody> recipes = service
                .getCustomerRecipes(username, pageable)
                .map(recipeToCustomerRecipeMapper::mapToDTO);
        final PageResponse<CustomerRecipeResponseBody> responseBody = PageResponse.from(recipes);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<SearchRecipeResponseBody>> searchRecipes(@RequestParam(
                                                                                        required = false,
                                                                                        defaultValue = ""
                                                                                ) String query,
                                                                                @PageableDefault(
                                                                                        sort = "name",
                                                                                        direction = Sort.Direction.ASC
                                                                                ) Pageable pageable) {
        final Page<SearchRecipeResponseBody> recipes = service
                .searchRecipes(query, pageable)
                .map(recipeToSearchRecipeMapper::mapToDTO);
        final PageResponse<SearchRecipeResponseBody> responseBody = PageResponse.from(recipes);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipeDetails(Authentication authentication,
                                                    @PathVariable Long id,
                                                    @Valid @RequestBody CustomerRecipeRequestBody requestBody) {
        final String username = authentication.getName();
        service.updateRecipe(username, id, requestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateRecipeImage(Authentication authentication,
                                                  @PathVariable Long id,
                                                  @RequestPart MultipartFile image) {
        final String username = authentication.getName();
        service.updateImage(username, id, image);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/image/restore")
    public ResponseEntity<Void> restoreDefaultRecipeImage(Authentication authentication,
                                                          @PathVariable Long id) {
        final String username = authentication.getName();
        service.restoreDefaultImage(username, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(Authentication authentication,
                                             @PathVariable Long id) {
        final String username = authentication.getName();
        service.deleteRecipe(username, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
