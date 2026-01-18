package com.askie01.recipeapplication.aspect;

import com.askie01.recipeapplication.dto.RecipeDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class RecipeRestControllerV1LoggingAspect {

    private static final String CREATE_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV1.createRecipe(..))";
    private static final String GET_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV1.getRecipe(..))";
    private static final String GET_RECIPES_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV1.getRecipes(..))";
    private static final String UPDATE_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV1.updateRecipe(..))";
    private static final String DELETE_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV1.deleteRecipe(..))";

    @Before(CREATE_RECIPE_POINTCUT)
    public void logBeforeCreateRecipe(JoinPoint joinPoint) {
        final String requestBody = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Received createRecipe POST request with request body: '{}'", requestBody);
    }

    @AfterReturning(
            pointcut = CREATE_RECIPE_POINTCUT,
            returning = "result"
    )
    public void logAfterReturningCreateRecipe(ResponseEntity<RecipeDTO> result) {
        final RecipeDTO responseBody = result.getBody();
        log.atInfo().log("Completed createRecipe POST request and returned: '{}'", responseBody);
    }

    @AfterThrowing(CREATE_RECIPE_POINTCUT)
    public void logAfterThrowingCreateRecipe(JoinPoint joinPoint) {
        final String requestBody = joinPoint.getArgs()[0].toString();
        log.atError().log("Error occurred while processing createRecipe POST request with request body: '{}'", requestBody);
    }

    @Before(GET_RECIPE_POINTCUT)
    public void logBeforeGetRecipe(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        log.atInfo().log("Received getRecipe GET request with ID: '{}'", id);
    }

    @AfterReturning(
            pointcut = GET_RECIPE_POINTCUT,
            returning = "result"
    )
    public void logAfterReturningGetRecipe(ResponseEntity<RecipeDTO> result) {
        final RecipeDTO responseBody = result.getBody();
        log.atInfo().log("Completed getRecipe GET request and returned: '{}'", responseBody);
    }

    @AfterThrowing(GET_RECIPE_POINTCUT)
    public void logAfterThrowingGetRecipe(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        log.atError().log("Error occurred while processing getRecipe GET request with ID: '{}'", id);
    }

    @Before(GET_RECIPES_POINTCUT)
    public void logBeforeGetRecipes() {
        log.atInfo().log("Received getRecipes GET request");
    }

    @AfterReturning(
            pointcut = GET_RECIPES_POINTCUT,
            returning = "result"
    )
    public void logAfterReturningGetRecipes(ResponseEntity<List<RecipeDTO>> result) {
        final List<RecipeDTO> responseBody = result.getBody();
        log.atInfo().log("Completed getRecipes GET request and returned: '{}'", responseBody);
    }

    @AfterThrowing(GET_RECIPES_POINTCUT)
    public void logAfterThrowingGetRecipes() {
        log.atError().log("Error occurred while processing getRecipes GET request");
    }

    @Before(UPDATE_RECIPE_POINTCUT)
    public void logBeforeUpdateRecipe(JoinPoint joinPoint) {
        final String requestBody = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Received updateRecipe PUT request with request body: '{}'", requestBody);
    }

    @AfterReturning(
            pointcut = UPDATE_RECIPE_POINTCUT,
            returning = "result"
    )
    public void logAfterReturningUpdateRecipe(ResponseEntity<RecipeDTO> result) {
        final RecipeDTO responseBody = result.getBody();
        log.atInfo().log("Completed updateRecipe PUT request and returned: '{}'", responseBody);
    }

    @AfterThrowing(UPDATE_RECIPE_POINTCUT)
    public void logAfterThrowingUpdateRecipe(JoinPoint joinPoint) {
        final String requestBody = joinPoint.getArgs()[0].toString();
        log.atError().log("Error occurred while processing updateRecipe PUT request with request body: '{}'", requestBody);
    }

    @Before(DELETE_RECIPE_POINTCUT)
    public void logBeforeDeleteRecipe(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        log.atInfo().log("Received deleteRecipe DELETE request with ID: '{}'", id);
    }

    @AfterReturning(
            pointcut = DELETE_RECIPE_POINTCUT,
            returning = "result"
    )
    public void logAfterReturningDeleteRecipe(ResponseEntity<RecipeDTO> result) {
        final RecipeDTO responseBody = result.getBody();
        log.atInfo().log("Completed deleteRecipe DELETE request and returned: '{}'", responseBody);
    }

    @AfterThrowing(DELETE_RECIPE_POINTCUT)
    public void logAfterThrowingDeleteRecipe(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        log.atError().log("Error occurred while processing deleteRecipe DELETE request with ID: '{}'", id);
    }
}
