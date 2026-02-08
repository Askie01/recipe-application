package com.askie01.recipeapplication.api.v1;

import com.askie01.recipeapplication.dto.UserRequestBody;
import com.askie01.recipeapplication.dto.UserResponseBody;
import com.askie01.recipeapplication.mapper.UserToUserResponseBodyMapper;
import com.askie01.recipeapplication.model.entity.User;
import com.askie01.recipeapplication.service.UserServiceV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "api.user.v1.enabled", havingValue = "true", matchIfMissing = true)
public class UserRestControllerV1 {

    private final UserServiceV1 service;
    private final UserToUserResponseBodyMapper mapper;

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserRequestBody requestBody) {
        service.createUser(requestBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserResponseBody> getUser(Authentication authentication) {
        final String username = authentication.getName();
        final User user = service.getUser(username);
        final UserResponseBody responseBody = mapper.mapToDTO(user);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(Authentication authentication,
                                           @Valid @RequestBody UserRequestBody requestBody) {
        final String username = authentication.getName();
        service.updateUser(username, requestBody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        final String username = authentication.getName();
        service.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
