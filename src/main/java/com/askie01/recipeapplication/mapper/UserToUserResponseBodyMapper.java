package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.dto.UserResponseBody;
import com.askie01.recipeapplication.model.entity.User;

public interface UserToUserResponseBodyMapper
        extends Mapper<User, UserResponseBody>,
        ToDTOMapper<User, UserResponseBody> {
}
