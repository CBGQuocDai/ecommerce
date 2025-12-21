package com.backend.service;

import com.backend.dto.request.cart.UpdateQuantityItemRequest;
import com.backend.dto.response.cart.CartItemResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    List<CartItemResponse> getCartItem();
    void addToCart(long skuId);
    void removeFromCart(long itemId);
    void clearCart();
    void updateItemQuantity(UpdateQuantityItemRequest req);
}
