package com.backend.dto.request.product;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record CreateProductRequest (
        String name, double price, String description,
        List<MultipartFile> images, long categoryId,
        String skus
) {
}
