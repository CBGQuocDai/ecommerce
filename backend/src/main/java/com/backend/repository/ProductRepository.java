package com.backend.repository;

import com.backend.entity.Product;
import com.backend.entity.Seller;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(long id);

    List<Product> findAllProductByOwner(Seller s, Pageable pageable);
}
