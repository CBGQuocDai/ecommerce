package com.backend.service;

import com.backend.dto.request.product.CreateProductRequest;
import com.backend.dto.response.product.ProductResponse;
import com.backend.dto.response.product.SellerProductResponse;
import com.backend.dto.response.product.SkuResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    void createProduct(CreateProductRequest request);
    void deleteProduct(Long id);
    Resource getImage(Long id);
    List<ProductResponse> buyerGetProducts(int page, int size);
    SkuResponse buyerGetSku(int id);
    List<SellerProductResponse> sellerGetProducts(int page, int size);
    List<ProductResponse> searchProducts(String nameProduct, int page, int size);
}
