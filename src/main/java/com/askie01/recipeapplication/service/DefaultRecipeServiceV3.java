package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.CustomerRecipeRequestBody;
import com.askie01.recipeapplication.exception.RecipeNotFoundException;
import com.askie01.recipeapplication.mapper.CustomerRecipeRequestBodyToRecipeMapper;
import com.askie01.recipeapplication.model.entity.Customer;
import com.askie01.recipeapplication.model.entity.Recipe;
import com.askie01.recipeapplication.repository.CustomerRepositoryV1;
import com.askie01.recipeapplication.repository.RecipeRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RequiredArgsConstructor
public class DefaultRecipeServiceV3 implements RecipeServiceV3 {

    private final RecipeRepositoryV3 recipeRepository;
    private final CustomerRepositoryV1 customerRepository;
    private final CustomerRecipeRequestBodyToRecipeMapper customerRecipeRequestBodyMapper;
    private final ClassPathResource classPathResource = new ClassPathResource("static/default-recipe.png");

    @Override
    @SneakyThrows
    public void createRecipe(String username, CustomerRecipeRequestBody requestBody) {
        final Customer customer = customerRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: '" + username + "' does not exist"));
        final Recipe recipe = customerRecipeRequestBodyMapper.mapToEntity(requestBody);
        final byte[] defaultImage = classPathResource.getContentAsByteArray();
        recipe.setImage(defaultImage);
        customer.getRecipes().add(recipe);
        customerRepository.save(customer);
    }

    @Override
    public Recipe getRecipe(Long id) {
        return recipeRepository
                .findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public byte[] getRecipeImage(Long id) {
        return recipeRepository
                .findRecipeImage(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }

    @Override
    public Page<Recipe> getCustomerRecipes(String username, Pageable pageable) {
        return recipeRepository.findByUsername(username, pageable);
    }

    @Override
    public Page<Recipe> searchRecipes(String query, Pageable pageable) {
        return recipeRepository.searchRecipes(query, pageable);
    }

    @Override
    public void updateRecipe(String username, Long id, CustomerRecipeRequestBody requestBody) {
        final Customer customer = customerRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: '" + username + "' does not exist"));
        final Recipe recipeToUpdate = customer.getRecipes()
                .stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecipeNotFoundException(id));
        customerRecipeRequestBodyMapper.map(requestBody, recipeToUpdate);
        recipeRepository.save(recipeToUpdate);
    }

    @Override
    @SneakyThrows
    public void updateImage(String username, Long id, MultipartFile image) {
        final Recipe recipe = findCustomerRecipe(username, id);
        final boolean isCorrectFormat = Objects
                .requireNonNull(image.getContentType())
                .startsWith("image/");
        if (isCorrectFormat) {
            final byte[] newImage = image.getBytes();
            recipe.setImage(newImage);
            recipeRepository.save(recipe);
        } else {
            throw new IllegalArgumentException("File: '" + image.getOriginalFilename() + "' is not jpeg");
        }
    }

    @Override
    @SneakyThrows
    public void restoreDefaultImage(String username, Long id) {
        final Recipe recipe = findCustomerRecipe(username, id);
        final byte[] defaultImage = classPathResource.getContentAsByteArray();
        recipe.setImage(defaultImage);
        recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(String username, Long id) {
        final Customer customer = customerRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: '" + username + "' does not exist"));
        final Recipe recipeToRemove = customer.getRecipes()
                .stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecipeNotFoundException(id));
        customer.getRecipes().remove(recipeToRemove);
        customerRepository.save(customer);
    }

    private Recipe findCustomerRecipe(String username, Long id) {
        return customerRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: '" + username + "' does not exist")).getRecipes()
                .stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }
}
