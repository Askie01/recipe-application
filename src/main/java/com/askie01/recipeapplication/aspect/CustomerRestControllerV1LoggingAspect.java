package com.askie01.recipeapplication.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CustomerRestControllerV1LoggingAspect {
    private static final String CREATE_CUSTOMER_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.CustomerRestControllerV1.createCustomer(..))";
    private static final String GET_CUSTOMER_DETAILS_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.CustomerRestControllerV1.getCustomerDetails(..))";
    private static final String GET_CUSTOMER_IMAGE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.CustomerRestControllerV1.getCustomerImage(..))";
    private static final String SEARCH_CUSTOMERS_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.CustomerRestControllerV1.searchCustomers(..))";
    private static final String GET_CUSTOMER_PROFILE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.CustomerRestControllerV1.getCustomerProfile(..))";
    private static final String UPDATE_CUSTOMER_DETAILS_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.CustomerRestControllerV1.updateCustomerDetails(..))";
    private static final String UPDATE_CUSTOMER_PASSWORD_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.CustomerRestControllerV1.updateCustomerPassword(..))";
    private static final String UPDATE_CUSTOMER_IMAGE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.CustomerRestControllerV1.updateCustomerImage(..))";
    private static final String RESTORE_DEFAULT_IMAGE_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.CustomerRestControllerV1.restoreDefaultImage(..))";
    private static final String DELETE_CUSTOMER_POINTCUT = "execution(* com.askie01.recipeapplication.api.*.CustomerRestControllerV1.deleteCustomer(..))";

    @Before(CREATE_CUSTOMER_POINTCUT)
    public void logBeforeCreateCustomer(JoinPoint joinPoint) {
        final String requestBody = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Received createCustomer POST request with request body: '{}'", requestBody);
    }

    @Around(CREATE_CUSTOMER_POINTCUT)
    public Object logAndRethrowCreateCustomer(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final String requestBody = proceedingJoinPoint.getArgs()[0].toString();
            log.atError().log("Error occurred while processing createCustomer POST request with request body: '{}'", requestBody);
            throw exception;
        }
    }

    @After(CREATE_CUSTOMER_POINTCUT)
    public void logAfterCreateCustomer(JoinPoint joinPoint) {
        final String requestBody = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Completed createCustomer POST request with request body: '{}'", requestBody);
    }

    @Before(GET_CUSTOMER_DETAILS_POINTCUT)
    public void logBeforeGetCustomerDetails(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        log.atInfo().log("Received getCustomerDetails GET request with username: '{}'", username);
    }

    @Around(GET_CUSTOMER_DETAILS_POINTCUT)
    public Object logAndRethrowGetCustomerDetails(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            log.atError().log("Error occurred while processing getCustomerDetails GET request with username: '{}'", username);
            throw exception;
        }
    }

    @After(GET_CUSTOMER_DETAILS_POINTCUT)
    public void logAfterGetCustomerDetails(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        log.atInfo().log("Completed getCustomerDetails GET request with username: '{}'", username);
    }

    @Before(GET_CUSTOMER_IMAGE_POINTCUT)
    public void logBeforeGetCustomerImage(JoinPoint joinPoint) {
        final String username = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Received getCustomerImage GET request with username: '{}'", username);
    }

    @Around(GET_CUSTOMER_IMAGE_POINTCUT)
    public Object logAndRethrowGetCustomerImage(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final String username = proceedingJoinPoint.getArgs()[0].toString();
            log.atError().log("Error occurred while processing getCustomerImage GET request with username: '{}'", username);
            throw exception;
        }
    }

    @After(GET_CUSTOMER_IMAGE_POINTCUT)
    public void logAfterGetCustomerImage(JoinPoint joinPoint) {
        final String username = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Completed getCustomerImage GET request with username: '{}'", username);
    }

    @Before(SEARCH_CUSTOMERS_POINTCUT)
    public void logBeforeSearchCustomers(JoinPoint joinPoint) {
        final String query = joinPoint.getArgs()[0].toString();
        final Pageable pageable = (Pageable) joinPoint.getArgs()[1];
        log.atInfo().log("Received searchCustomers GET request with query: '{}' and pageable: '{}'", query, pageable);
    }

    @Around(SEARCH_CUSTOMERS_POINTCUT)
    public Object logAndRethrowSearchCustomers(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final String query = proceedingJoinPoint.getArgs()[0].toString();
            final Pageable pageable = (Pageable) proceedingJoinPoint.getArgs()[1];
            log.atError().log("Error occurred while processing searchCustomers GET request with query: '{}' and pageable: '{}'", query, pageable);
            throw exception;
        }
    }

    @After(SEARCH_CUSTOMERS_POINTCUT)
    public void logAfterSearchCustomers(JoinPoint joinPoint) {
        final String query = joinPoint.getArgs()[0].toString();
        final Pageable pageable = (Pageable) joinPoint.getArgs()[1];
        log.atInfo().log("Completed searchCustomers GET request with query: '{}' and pageable: '{}'", query, pageable);
    }

    @Before(GET_CUSTOMER_PROFILE_POINTCUT)
    public void logBeforeGetCustomerProfile(JoinPoint joinPoint) {
        final String username = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Received getCustomerProfile GET request with username: '{}'", username);
    }

    @Around(GET_CUSTOMER_PROFILE_POINTCUT)
    public Object logAndRethrowGetCustomerProfile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final String username = proceedingJoinPoint.getArgs()[0].toString();
            log.atError().log("Error occurred while processing getCustomerProfile GET request with username: '{}'", username);
            throw exception;
        }
    }

    @After(GET_CUSTOMER_PROFILE_POINTCUT)
    public void logAfterGetCustomerProfile(JoinPoint joinPoint) {
        final String username = joinPoint.getArgs()[0].toString();
        log.atInfo().log("Completed getCustomerProfile GET request with username: '{}'", username);
    }

    @Before(UPDATE_CUSTOMER_DETAILS_POINTCUT)
    public void logBeforeUpdateCustomerDetails(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final String requestBody = joinPoint.getArgs()[1].toString();
        log.atInfo().log("Received updateCustomerDetails PUT request with username: '{}' and request body: '{}'", username, requestBody);
    }

    @Around(UPDATE_CUSTOMER_DETAILS_POINTCUT)
    public Object logAndRethrowUpdateCustomerDetails(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            final String requestBody = proceedingJoinPoint.getArgs()[1].toString();
            log.atError().log("Error occurred while processing updateCustomerDetails PUT request with username: '{}' and request body: '{}'", username, requestBody);
            throw exception;
        }
    }

    @After(UPDATE_CUSTOMER_DETAILS_POINTCUT)
    public void logAfterUpdateCustomerDetails(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        final String requestBody = joinPoint.getArgs()[1].toString();
        log.atInfo().log("Completed updateCustomerDetails PUT request with username: '{}' and request body: '{}'", username, requestBody);
    }

    @Before(UPDATE_CUSTOMER_PASSWORD_POINTCUT)
    public void logBeforeUpdateCustomerPassword(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        log.atInfo().log("Received updateCustomerPassword PUT request with username: '{}'", username);
    }

    @Around(UPDATE_CUSTOMER_PASSWORD_POINTCUT)
    public Object logAndRethrowUpdateCustomerPassword(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            log.atError().log("Error occurred while processing updateCustomerPassword PUT request with username: '{}'", username);
            throw exception;
        }
    }

    @After(UPDATE_CUSTOMER_PASSWORD_POINTCUT)
    public void logAfterUpdateCustomerPassword(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        log.atInfo().log("Completed updateCustomerPassword PUT request with username: '{}'", username);
    }

    @Before(UPDATE_CUSTOMER_IMAGE_POINTCUT)
    public void logBeforeUpdateCustomerImage(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        log.atInfo().log("Received updateCustomerImage PUT request with username: '{}'", username);
    }

    @Around(UPDATE_CUSTOMER_IMAGE_POINTCUT)
    public Object logAndRethrowUpdateCustomerImage(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            log.atError().log("Error occurred while processing updateCustomerImage PUT request with username: '{}'", username);
            throw exception;
        }
    }

    @After(UPDATE_CUSTOMER_IMAGE_POINTCUT)
    public void logAfterUpdateCustomerImage(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        log.atInfo().log("Completed updateCustomerImage PUT request with username: '{}'", username);
    }

    @Before(RESTORE_DEFAULT_IMAGE_POINTCUT)
    public void logBeforeRestoreDefaultImage(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        log.atInfo().log("Received restoreDefaultImage PUT request with username: '{}'", username);
    }

    @Around(RESTORE_DEFAULT_IMAGE_POINTCUT)
    public Object logAndRethrowRestoreDefaultImage(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            log.atError().log("Error occurred while processing restoreDefaultImage PUT request with username: '{}'", username);
            throw exception;
        }
    }

    @After(RESTORE_DEFAULT_IMAGE_POINTCUT)
    public void logAfterRestoreDefaultImage(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        log.atInfo().log("Completed restoreDefaultImage PUT request with username: '{}'", username);
    }

    @Before(DELETE_CUSTOMER_POINTCUT)
    public void logBeforeDeleteCustomer(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        log.atInfo().log("Received deleteCustomer DELETE request with username: '{}'", username);
    }

    @Around(DELETE_CUSTOMER_POINTCUT)
    public Object logAndRethrowDeleteCustomer(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception exception) {
            final Authentication authentication = (Authentication) proceedingJoinPoint.getArgs()[0];
            final String username = authentication.getName();
            log.atError().log("Error occurred while processing deleteCustomer DELETE request with username: '{}'", username);
            throw exception;
        }
    }

    @After(DELETE_CUSTOMER_POINTCUT)
    public void logAfterDeleteCustomer(JoinPoint joinPoint) {
        final Authentication authentication = (Authentication) joinPoint.getArgs()[0];
        final String username = authentication.getName();
        log.atInfo().log("Completed deleteCustomer DELETE request with username: '{}'", username);
    }
}
