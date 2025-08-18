package com.askie01.recipeapplication.response.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class HttpResponseCode {
    public static final Integer OK = 200;
    public static final Integer CREATED = 201;
    public static final Integer NO_CONTENT = 204;
    public static final Integer BAD_REQUEST = 400;
    public static final Integer NOT_FOUND = 404;
    public static final Integer INTERNAL_SERVER_ERROR = 500;
}
