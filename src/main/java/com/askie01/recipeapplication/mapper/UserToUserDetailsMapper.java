package com.askie01.recipeapplication.mapper;

import com.askie01.recipeapplication.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserToUserDetailsMapper
        extends Mapper<User, UserDetails>,
        ToDTOMapper<User, UserDetails> {
}
