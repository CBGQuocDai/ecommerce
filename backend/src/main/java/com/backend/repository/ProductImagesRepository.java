package com.backend.repository;

import com.backend.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {
    ProductImages findProductImagesById(Long id);
}
