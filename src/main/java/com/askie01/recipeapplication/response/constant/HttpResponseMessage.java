package com.askie01.recipeapplication.response.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HttpResponseMessage {
    public static final String OK = "Request processed successfully";
    public static final String CREATED = "Resource created successfully";
    public static final String NO_CONTENT = "Request completed with no content";
    public static final String BAD_REQUEST = "Invalid request parameters";
    public static final String NOT_FOUND = "Requested resource not found";
    public static final String INTERNAL_SERVER_ERROR = "An unexpected error occurred";
}
