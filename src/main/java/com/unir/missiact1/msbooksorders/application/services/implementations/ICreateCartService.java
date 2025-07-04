package com.unir.missiact1.msbooksorders.application.services.implementations;

import java.util.UUID;
import com.unir.missiact1.msbooksorders.application.dtos.CartDto;
import com.unir.missiact1.msbooksorders.application.dtos.CartItemRequest;

public interface ICreateCartService {
    CartDto getOrCreateCart(UUID customerId);
    CartDto addItem(UUID customerId, CartItemRequest req);
    CartDto updateItem(UUID customerId, CartItemRequest req);
    CartDto removeItem(UUID customerId, UUID bookId);
    void clearCart(UUID customerId);
    CartDto decrementItem(UUID customerId, UUID bookId);
}