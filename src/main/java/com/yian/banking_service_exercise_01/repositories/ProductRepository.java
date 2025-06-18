package com.yian.banking_service_exercise_01.repositories;

import com.yian.banking_service_exercise_01.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByName(String name);
    Product findByName(String name);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);


}
