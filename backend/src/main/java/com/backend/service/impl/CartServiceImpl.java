package com.backend.service.impl;

import com.backend.dto.request.cart.UpdateQuantityItemRequest;
import com.backend.dto.response.cart.CartItemResponse;
import com.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    @Override
    public List<CartItemResponse> getCartItem() {
        return List.of();
    }

    @Override
    public void addToCart(long skuId) {

    }

    @Override
    public void removeFromCart(long itemId) {

    }

    @Override
    public void clearCart() {

    }

    @Override
    public void updateItemQuantity(UpdateQuantityItemRequest req) {

    }
}
