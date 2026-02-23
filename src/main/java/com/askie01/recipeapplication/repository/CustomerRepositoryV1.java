package com.askie01.recipeapplication.repository;

import com.askie01.recipeapplication.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepositoryV1 extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsernameIgnoreCase(String username);

    @Query("""
            SELECT DISTINCT c
            FROM Customer c
            WHERE LOWER(c.username) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    Page<Customer> findCustomer(@Param("query") String query, Pageable pageable);

    @Transactional
    void deleteByUsername(String username);
}
