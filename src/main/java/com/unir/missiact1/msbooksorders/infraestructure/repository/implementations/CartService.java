package com.unir.missiact1.msbooksorders.infraestructure.repository.implementations;

import java.math.BigDecimal;
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
    public CartDto addItem(UUID customerId, UUID bookId, Integer quantity, BigDecimal unitPrice) {
        Cart cart = repo.findByCustomerId(customerId)
                .orElseGet(() -> repo.save(new Cart(customerId)));
        cart.getItems().stream()
                .filter(i -> i.getBookId().equals(bookId))
                .findFirst()
                .ifPresentOrElse(i -> {
                    i.setQuantity(i.getQuantity() + quantity);
                    i.setUnitPrice(unitPrice);
                }, () -> {
                    cart.addItem(new CartItem(bookId, quantity, unitPrice));
                });
        return CartMapper.toDto(repo.save(cart));
    }

    @Transactional
    public CartDto updateItem(UUID customerId, UUID bookId, Integer quantity, BigDecimal unitPrice) {
        Cart cart = repo.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomException("Carrito no encontrado", ApiErrorCode.NotFound));
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getBookId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new CustomException("Ítem no encontrado", ApiErrorCode.NotFound));
        item.setQuantity(quantity);
        item.setUnitPrice(unitPrice);
        return CartMapper.toDto(repo.save(cart));
    }

    @Transactional
    public CartDto removeItem(UUID customerId, UUID bookId) {
        Cart cart = repo.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomException("Carrito no encontrado", ApiErrorCode.NotFound));
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getBookId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new CustomException("Ítem no encontrado", ApiErrorCode.NotFound));
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

    @Transactional
    public CartDto decrementItem(UUID customerId, UUID bookId) {
        Cart cart = repo.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomException("Carrito no encontrado", ApiErrorCode.NotFound));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getBookId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new CustomException("Ítem no encontrado", ApiErrorCode.NotFound));

        // Reducir cantidad en 1
        int nuevaCantidad = item.getQuantity() - 1;
        if (nuevaCantidad > 0) {
            item.setQuantity(nuevaCantidad);
        } else {
            // si llega a cero o menos, lo quitamos del carrito
            cart.removeItem(item);
        }

        return CartMapper.toDto(repo.save(cart));
    }
}