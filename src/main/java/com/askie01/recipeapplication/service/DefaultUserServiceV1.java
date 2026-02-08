package com.askie01.recipeapplication.service;

import com.askie01.recipeapplication.dto.UserRequestBody;
import com.askie01.recipeapplication.model.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultUserServiceV1 implements UserServiceV1 {

    @Override
    public void createUser(UserRequestBody requestBody) {

    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void updateUser(String username, UserRequestBody requestBody) {

    }

    @Override
    public void deleteUser(String username) {

    }
}
