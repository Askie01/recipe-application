package com.askie01.recipeapplication.exception;

import com.askie01.recipeapplication.response.ErrorHttpResponse;
import com.askie01.recipeapplication.response.constant.HttpResponseCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        final Map<String, String> validationErrors = new HashMap<>();
        final List<ObjectError> validationErrorsList = exception.getBindingResult().getAllErrors();
        validationErrorsList.forEach(error -> {
            final String fieldName = ((FieldError) error).getField();
            final String validationMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMessage);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorHttpResponse> handleGlobalException(Exception exception,
                                                                   WebRequest request) {
        final ErrorHttpResponse errorHttpResponse = createErrorResponse(exception, request, HttpResponseCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorHttpResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {
            RecipeNotFoundException.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<ErrorHttpResponse> handleNotFoundException(Throwable throwable,
                                                                     WebRequest request) {
        final ErrorHttpResponse errorHttpResponse = createErrorResponse(throwable, request, HttpResponseCode.NOT_FOUND);
        return new ResponseEntity<>(errorHttpResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorHttpResponse> handleBadRequestException(Throwable throwable,
                                                                       WebRequest request) {
        final ErrorHttpResponse errorHttpResponse = createErrorResponse(throwable, request, HttpResponseCode.BAD_REQUEST);
        return new ResponseEntity<>(errorHttpResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorHttpResponse createErrorResponse(Throwable throwable, WebRequest request, Integer responseCode) {
        final String path = request.getDescription(false);
        final String exceptionMessage = throwable.getMessage();
        final LocalDateTime errorTimestamp = LocalDateTime.now();
        return ErrorHttpResponse.builder()
                .path(path)
                .code(responseCode)
                .message(exceptionMessage)
                .timestamp(errorTimestamp)
                .build();
    }
}
