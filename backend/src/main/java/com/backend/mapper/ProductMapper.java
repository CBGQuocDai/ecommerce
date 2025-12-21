package com.backend.mapper;

import com.backend.dto.response.product.ProductResponse;
import com.backend.dto.response.product.SellerProductResponse;
import com.backend.entity.Product;

public interface ProductMapper {
    ProductResponse fromProductToProductResponse(Product product);
    SellerProductResponse fromProductToSellerProductResponse(Product product);
}
