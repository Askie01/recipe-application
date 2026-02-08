package com.askie01.recipeapplication.repository;

import com.askie01.recipeapplication.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryV1 extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
