package com.unir.missiact1.msbooksorders.infraestructure.repository.implementations;

import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unir.missiact1.msbooksorders.application.dtos.*;
import com.unir.missiact1.msbooksorders.application.request.mapster.CartMapper;
import com.unir.missiact1.msbooksorders.commons.exceptions.CustomException;
import com.unir.missiact1.msbooksorders.commons.enums.ApiErrorCode;
import com.unir.missiact1.msbooksorders.domain.*;
import com.unir.missiact1.msbooksorders.domain.interfaces.ICartRepository;

@Service
public class CartService {
    private final ICartRepository repo;

    public CartService(ICartRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public CartDto getCart(UUID customerId) {
        Cart cart = repo.findByCustomerId(customerId)
                       .orElseGet(() -> repo.save(new Cart(customerId)));
        return CartMapper.toDto(cart);
    }

    @Transactional
    public CartDto addItem(UUID customerId, CartItemRequest r) {
        Cart cart = repo.findByCustomerId(customerId)
                       .orElseGet(() -> repo.save(new Cart(customerId)));

        cart.getItems().stream()
            .filter(i -> i.getBookId().equals(r.getBookId()))
            .findFirst()
            .ifPresentOrElse(i -> {
                i.setQuantity(i.getQuantity() + r.getQuantity());
                i.setUnitPrice(r.getUnitPrice());
            }, () -> {
                cart.addItem(new CartItem(r.getBookId(), r.getQuantity(), r.getUnitPrice()));
            });

        return CartMapper.toDto(repo.save(cart));
    }

    @Transactional
    public CartDto updateItem(UUID customerId, CartItemRequest r) {
        Cart cart = repo.findByCustomerId(customerId)
            .orElseThrow(() -> new CustomException("Carrito no encontrado", ApiErrorCode.NotFound));

        CartItem item = cart.getItems().stream()
            .filter(i -> i.getBookId().equals(r.getBookId()))
            .findFirst()
            .orElseThrow(() -> new CustomException("Ítem no encontrado en el carrito", ApiErrorCode.NotFound));

        item.setQuantity(r.getQuantity());
        item.setUnitPrice(r.getUnitPrice());
        return CartMapper.toDto(repo.save(cart));
    }

    @Transactional
    public CartDto removeItem(UUID customerId, UUID bookId) {
        Cart cart = repo.findByCustomerId(customerId)
            .orElseThrow(() -> new CustomException("Carrito no encontrado", ApiErrorCode.NotFound));

        CartItem item = cart.getItems().stream()
            .filter(i -> i.getBookId().equals(bookId))
            .findFirst()
            .orElseThrow(() -> new CustomException("Ítem no encontrado en el carrito", ApiErrorCode.NotFound));

        cart.removeItem(item);
        return CartMapper.toDto(repo.save(cart));
    }

    @Transactional
    public void clearCart(UUID customerId) {
        Cart cart = repo.findByCustomerId(customerId)
            .orElseThrow(() -> new CustomException("Carrito no encontrado", ApiErrorCode.NotFound));
        cart.getItems().clear();
        repo.save(cart);
    }
}