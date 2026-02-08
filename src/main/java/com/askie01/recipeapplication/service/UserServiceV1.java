package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.UserRequestBody;
import com.askie01.recipeapplication.model.entity.User;

public interface UserServiceV1 {

    void createUser(UserRequestBody requestBody);

    User getUser(String username);

    void updateUser(String username, UserRequestBody requestBody);

    void deleteUser(String username);
}
