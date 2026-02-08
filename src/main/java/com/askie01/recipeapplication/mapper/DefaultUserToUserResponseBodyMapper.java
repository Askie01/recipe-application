package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.UserResponseBody;
import com.askie01.recipeapplication.model.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultUserToUserResponseBodyMapper implements UserToUserResponseBodyMapper {

    @Override
    public UserResponseBody mapToDTO(User user) {
        return null;
    }

    @Override
    public void map(User user, UserResponseBody userResponseBody) {

    }
}
