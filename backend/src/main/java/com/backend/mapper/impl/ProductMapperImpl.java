package com.backend.mapper.impl;

import com.backend.dto.response.product.ProductResponse;
import com.backend.dto.response.product.SellerProductResponse;
import com.backend.entity.Product;
import com.backend.entity.ProductImages;
import com.backend.mapper.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponse fromProductToProductResponse(Product p) {
        List<Long> imagesUrl = p.getImages().stream().map(ProductImages::getId).toList();
        return new ProductResponse(p.getId(),p.getName(),
                p.getPrice(),p.getRate(),p.getDescription(),imagesUrl,p.getCategory(),p.getOwner());
    }

    @Override
    public SellerProductResponse fromProductToSellerProductResponse(Product p) {
        List<Long> imagesUrl = p.getImages().stream().map(ProductImages::getId).toList();
        return new SellerProductResponse(p.getId(),p.getName(),
                p.getPrice(),p.getRate(),p.getDescription(),imagesUrl,p.getCategory());
    }
}
