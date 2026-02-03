package com.askie01.recipeapplication.aspect;

import com.askie01.recipeapplication.dto.RecipeResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class RecipeRestControllerV2LoggingAspect {

    private static final String CREATE_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV2.createRecipe(..))";
    private static final String GET_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV2.getRecipe(..))";
    private static final String GET_RECIPES_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV2.getRecipes(..))";
    private static final String SEARCH_RECIPES_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV2.searchRecipes(..))";
    private static final String UPDATE_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV2.updateRecipe(..))";
    private static final String DELETE_RECIPE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.RecipeRestControllerV2.deleteRecipe(..))";

    @Before(CREATE_RECIPE_POINTCUT)
    public void logBeforeCreateRecipe(JoinPoint joinPoint) {
        final String requestBody = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Received createRecipe POST request with request body: '{}'", requestBody);
    }

    @Around(CREATE_RECIPE_POINTCUT)
    public Object logAndRethrowCreateRecipe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final String requestBody = proceedingJoinPoint.getArgs()[0].toString();
            log.atError().log("Error occurred while processing createRecipe POST request with request body: '{}'", requestBody);
            throw exception;
        }
    }

    @After(CREATE_RECIPE_POINTCUT)
    public void logAfterCreateRecipe(JoinPoint joinPoint) {
        final String requestBody = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Completed createRecipe POST with request body: '{}'", requestBody);
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

    @AfterReturning(
            pointcut = GET_RECIPE_POINTCUT,
            returning = "response"
    )
    public void logAfterReturningGetRecipe(JoinPoint joinPoint,
                                           ResponseEntity<RecipeResponseBody> response) {
        final Long id = (Long) joinPoint.getArgs()[0];
        final RecipeResponseBody responseBody = response.getBody();
        log.atInfo().log("Completed getRecipe GET request with ID: '{}' and returned: '{}'", id, responseBody);
    }

    @Before(GET_RECIPES_POINTCUT)
    public void logBeforeGetRecipes(JoinPoint joinPoint) {
        final Integer pageNumber = (Integer) joinPoint.getArgs()[0];
        final Integer pageSize = (Integer) joinPoint.getArgs()[1];
        log.atInfo().log("Received getRecipes GET request with pageNumber: '{}' and pageSize: '{}'", pageNumber, pageSize);
    }

    @Around(GET_RECIPES_POINTCUT)
    public Object logAndRethrowGetRecipes(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Integer pageNumber = (Integer) proceedingJoinPoint.getArgs()[0];
            final Integer pageSize = (Integer) proceedingJoinPoint.getArgs()[1];
            log.atError().log("Error occurred while processing getRecipes GET request with pageNumber: '{}' and pageSize: '{}'", pageNumber, pageSize);
            throw exception;
        }
    }

    @AfterReturning(
            pointcut = GET_RECIPES_POINTCUT,
            returning = "response"
    )
    public void logAfterReturningGetRecipes(JoinPoint joinPoint,
                                            ResponseEntity<List<RecipeResponseBody>> response) {
        final Integer pageNumber = (Integer) joinPoint.getArgs()[0];
        final Integer pageSize = (Integer) joinPoint.getArgs()[1];
        final List<String> recipeNames = response.getBody()
                .stream()
                .map(RecipeResponseBody::getName)
                .toList();
        log.atInfo().log("Completed getRecipes GET request with pageNumber: '{}' and pageSize: '{}' and returned: '{}'", pageNumber, pageSize, recipeNames);
    }

    @Before(SEARCH_RECIPES_POINTCUT)
    public void logBeforeSearchRecipes(JoinPoint joinPoint) {
        final String text = joinPoint.getArgs()[0].toString();
        final Integer pageNumber = (Integer) joinPoint.getArgs()[1];
        final Integer pageSize = (Integer) joinPoint.getArgs()[2];
        log.atInfo().log("Received searchRecipes GET request with text: '{}' and pageNumber: '{}' and pageSize: '{}'", text, pageNumber, pageSize);
    }

    @Around(SEARCH_RECIPES_POINTCUT)
    public Object logAndRethrowSearchRecipes(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final String text = proceedingJoinPoint.getArgs()[0].toString();
            final Integer pageNumber = (Integer) proceedingJoinPoint.getArgs()[1];
            final Integer pageSize = (Integer) proceedingJoinPoint.getArgs()[2];
            log.atError().log("Error occurred while processing searchRecipes GET request with text: '{}' and pageNumber: '{}' and pageSize: '{}'", text, pageNumber, pageSize);
            throw exception;
        }
    }

    @AfterReturning(
            pointcut = SEARCH_RECIPES_POINTCUT,
            returning = "response"
    )
    public void logAfterReturningSearchRecipes(JoinPoint joinPoint,
                                               ResponseEntity<List<RecipeResponseBody>> response) {
        final String text = joinPoint.getArgs()[0].toString();
        final Integer pageNumber = (Integer) joinPoint.getArgs()[1];
        final Integer pageSize = (Integer) joinPoint.getArgs()[2];
        final List<String> recipeNames = response.getBody()
                .stream()
                .map(RecipeResponseBody::getName)
                .toList();
        log.atInfo().log("Completed searchRecipes GET request with text: '{}' and pageNumber: '{}' and pageSize: '{}' and returned: '{}'", text, pageNumber, pageSize, recipeNames);
    }

    @Before(UPDATE_RECIPE_POINTCUT)
    public void logBeforeUpdateRecipe(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        final String requestBody = joinPoint.getArgs()[1].toString();
        log.atInfo().log("Received updateRecipe PUT request with ID: '{}' and request body: '{}'", id, requestBody);
    }

    @Around(UPDATE_RECIPE_POINTCUT)
    public Object logAndRethrowUpdateRecipe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Long id = (Long) proceedingJoinPoint.getArgs()[0];
            final String requestBody = proceedingJoinPoint.getArgs()[1].toString();
            log.atError().log("Error occurred while processing updateRecipe PUT request with ID: '{}' and request body: '{}'", id, requestBody);
            throw exception;
        }
    }

    @After(UPDATE_RECIPE_POINTCUT)
    public void logAfterUpdateRecipe(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        final String requestBody = joinPoint.getArgs()[1].toString();
        log.atInfo().log("Completed updateRecipe PUT request with ID: '{}' and request body: '{}'", id, requestBody);
    }

    @Before(DELETE_RECIPE_POINTCUT)
    public void logBeforeDeleteRecipe(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        log.atInfo().log("Received deleteRecipe DELETE request with ID: '{}'", id);
    }

    @Around(DELETE_RECIPE_POINTCUT)
    public void logAndRethrowDeleteRecipe(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Long id = (Long) proceedingJoinPoint.getArgs()[0];
            log.atError().log("Error occurred while processing deleteRecipe DELETE request with ID: '{}'", id);
            throw exception;
        }
    }

    @After(DELETE_RECIPE_POINTCUT)
    public void logAfterDeleteRecipe(JoinPoint joinPoint) {
        final Long id = (Long) joinPoint.getArgs()[0];
        log.atInfo().log("Completed deleteRecipe DELETE request with ID: '{}'", id);
    }
}
