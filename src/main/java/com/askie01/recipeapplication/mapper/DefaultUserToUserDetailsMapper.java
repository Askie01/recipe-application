package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public class DefaultUserToUserDetailsMapper implements UserToUserDetailsMapper {

    @Override
    public UserDetails mapToDTO(User user) {
        return null;
    }

    @Override
    public void map(User user, UserDetails userDetails) {

    }
}
