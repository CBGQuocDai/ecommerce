package com.backend.dto.response.product;

import com.backend.entity.Category;
import com.backend.entity.Seller;

import java.util.List;

public record SellerProductResponse(long id, String name,
                                    double price, double rate,
                                    String description, List<Long> images,
                                    Category category
) {
}
