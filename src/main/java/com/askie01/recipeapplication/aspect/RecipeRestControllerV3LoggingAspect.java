package com.askie01.recipeapplication.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RecipeRestControllerV3LoggingAspect {
    private static final String CREATE_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV3.createRecipe(..))";
    private static final String GET_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV3.getRecipe(..))";
    private static final String GET_RECIPE_IMAGE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV3.getRecipeImage(..))";
    private static final String GET_USER_RECIPES_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV3.getUserRecipes(..))";
    private static final String SEARCH_USER_RECIPES_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV3.searchRecipes(..))";
    private static final String UPDATE_RECIPE_DETAILS_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV3.updateRecipeDetails(..))";
    private static final String UPDATE_RECIPE_IMAGE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV3.updateRecipeImage(..))";
    private static final String RESTORE_DEFAULT_RECIPE_IMAGE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV3.restoreDefaultRecipeImage(..))";
    private static final String DELETE_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV3.deleteRecipe(..))";

    @Before(CREATE_RECIPE_POINTCUT)
    public void logBeforeCreateRecipe(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final String requestBody = joinPoint.getArgs()[1].toString();
        log.atInfo().log("Received createRecipe POST request with username: '{}' and request body: '{}'", username, requestBody);
    }

    @Around(CREATE_RECIPE_POINTCUT)
    public Object logAndRethrowCreateRecipe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            final String requestBody = proceedingJoinPoint.getArgs()[1].toString();
            log.atError().log("Error occurred while processing createRecipe POST request with username: '{}' and request body: '{}'", username, requestBody);
            throw exception;
        }
    }

    @After(CREATE_RECIPE_POINTCUT)
    public void logAfterCreateRecipe(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final String requestBody = joinPoint.getArgs()[1].toString();
        log.atInfo().log("Completed createRecipe POST request with username: '{}' and request body: '{}'", username, requestBody);
    }

    @Before(GET_RECIPE_POINTCUT)
    public void logBeforeGetRecipe(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        log.atInfo().log("Received getRecipe GET request with ID: '{}'", id);
    }

    @Around(GET_RECIPE_POINTCUT)
    public Object logAndRethrowGetRecipe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Long id = (Long) proceedingJoinPoint.getArgs()[0];
            log.atError().log("Error occurred while processing getRecipe GET request with ID: '{}'", id);
            throw exception;
        }
    }

    @After(GET_RECIPE_POINTCUT)
    public void logAfterGetRecipe(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        log.atInfo().log("Completed getRecipe GET request with ID: '{}'", id);
    }

    @Before(GET_RECIPE_IMAGE_POINTCUT)
    public void logBeforeGetRecipeImage(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        log.atInfo().log("Received getRecipeImage GET request with ID: '{}'", id);
    }

    @Around(GET_RECIPE_IMAGE_POINTCUT)
    public Object logAndRethrowGetRecipeImage(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Long id = (Long) proceedingJoinPoint.getArgs()[0];
            log.atError().log("Error occurred while processing getRecipeImage GET request with ID: '{}'", id);
            throw exception;
        }
    }

    @After(GET_RECIPE_IMAGE_POINTCUT)
    public void logAfterGetRecipeImage(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        log.atInfo().log("Completed getRecipeImage GET request with ID: '{}'", id);
    }

    @Before(GET_USER_RECIPES_POINTCUT)
    public void logBeforeGetUserRecipes(JoinPoint joinPoint) {
        final String username = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Received getUserRecipes GET request with username: '{}'", username);
    }

    @Around(GET_USER_RECIPES_POINTCUT)
    public Object logAndRethrowGetUserRecipes(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final String username = proceedingJoinPoint.getArgs()[0].toString();
            log.atError().log("Error occurred while processing getUserRecipes GET request with username: '{}'", username);
            throw exception;
        }
    }

    @After(GET_USER_RECIPES_POINTCUT)
    public void logAfterGetUserRecipes(JoinPoint joinPoint) {
        final String username = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Completed getUserRecipes GET request with username: '{}'", username);
    }

    @Before(SEARCH_USER_RECIPES_POINTCUT)
    public void logBeforeSearchUserRecipes(JoinPoint joinPoint) {
        final String query = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Received searchUserRecipes GET request with query: '{}'", query);
    }

    @Around(SEARCH_USER_RECIPES_POINTCUT)
    public Object logAndRethrowSearchUserRecipes(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final String query = proceedingJoinPoint.getArgs()[0].toString();
            log.atError().log("Error occurred while processing searchUserRecipes GET request with query: '{}'", query);
            throw exception;
        }
    }

    @After(SEARCH_USER_RECIPES_POINTCUT)
    public void logAfterSearchUserRecipes(JoinPoint joinPoint) {
        final String query = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Completed searchUserRecipes GET request with query: '{}'", query);
    }

    @Before(UPDATE_RECIPE_DETAILS_POINTCUT)
    public void logBeforeUpdateRecipeDetails(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final Long id = (Long) joinPoint.getArgs()[1];
        final String requestBody = joinPoint.getArgs()[2].toString();
        log.atInfo().log("Received updateRecipeDetails PUT request with username: '{}' and ID: '{}' and request body: '{}'", username, id, requestBody);
    }

    @Around(UPDATE_RECIPE_DETAILS_POINTCUT)
    public Object logAndRethrowUpdateRecipeDetails(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            final Long id = (Long) proceedingJoinPoint.getArgs()[1];
            final String requestBody = proceedingJoinPoint.getArgs()[2].toString();
            log.atError().log("Error occurred while processing updateRecipeDetails PUT request with username: '{}' and ID: '{}' and request body: '{}'", username, id, requestBody);
            throw exception;
        }
    }

    @After(UPDATE_RECIPE_DETAILS_POINTCUT)
    public void logAfterUpdateRecipeDetails(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final Long id = (Long) joinPoint.getArgs()[1];
        final String requestBody = joinPoint.getArgs()[2].toString();
        log.atInfo().log("Completed updateRecipeDetails PUT request with username: '{}' and ID: '{}' and request body: '{}'", username, id, requestBody);
    }

    @Before(UPDATE_RECIPE_IMAGE_POINTCUT)
    public void logBeforeUpdateRecipeImage(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final Long id = (Long) joinPoint.getArgs()[1];
        log.atInfo().log("Received updateRecipeImage PUT request with username: '{}' and ID: '{}'", username, id);
    }

    @Around(UPDATE_RECIPE_IMAGE_POINTCUT)
    public Object logAndRethrowUpdateRecipeImage(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            final Long id = (Long) proceedingJoinPoint.getArgs()[1];
            log.atError().log("Error occurred while processing updateRecipeImage PUT request with username: '{}' and ID: '{}'", username, id);
            throw exception;
        }
    }

    @After(UPDATE_RECIPE_IMAGE_POINTCUT)
    public void logAfterUpdateRecipeImage(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final Long id = (Long) joinPoint.getArgs()[1];
        log.atInfo().log("Completed updateRecipeImage PUT request with username: '{}' and ID: '{}'", username, id);
    }

    @Before(RESTORE_DEFAULT_RECIPE_IMAGE_POINTCUT)
    public void logBeforeRestoreDefaultRecipeImage(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final Long id = (Long) joinPoint.getArgs()[1];
        log.atInfo().log("Received restoreDefaultRecipeImage PUT request with username: '{}' and ID: '{}'", username, id);
    }

    @Around(RESTORE_DEFAULT_RECIPE_IMAGE_POINTCUT)
    public Object logAndRethrowRestoreDefaultRecipeImage(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            final Long id = (Long) proceedingJoinPoint.getArgs()[1];
            log.atError().log("Error occurred while processing restoreDefaultRecipeImage PUT request with username: '{}' and ID: '{}'", username, id);
            throw exception;
        }
    }

    @After(RESTORE_DEFAULT_RECIPE_IMAGE_POINTCUT)
    public void logAfterRestoreDefaultRecipeImage(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final Long id = (Long) joinPoint.getArgs()[1];
        log.atInfo().log("Completed restoreDefaultRecipeImage PUT request with username: '{}' and ID: '{}'", username, id);
    }

    @Before(DELETE_RECIPE_POINTCUT)
    public void logBeforeDeleteRecipe(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final Long id = (Long) joinPoint.getArgs()[1];
        log.atInfo().log("Received deleteRecipe DELETE request with username: '{}' and ID: '{}'", username, id);
    }

    @Around(DELETE_RECIPE_POINTCUT)
    public Object logAndRethrowDeleteRecipe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            final Long id = (Long) proceedingJoinPoint.getArgs()[1];
            log.atError().log("Error occurred while processing deleteRecipe DELETE request with username: '{}' and ID: '{}'", username, id);
            throw exception;
        }
    }

    @After(DELETE_RECIPE_POINTCUT)
    public void logAfterDeleteRecipe(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final Long id = (Long) joinPoint.getArgs()[1];
        log.atInfo().log("Completed deleteRecipe DELETE request with username: '{}' and ID: '{}'", username, id);
    }
}
