package com.unir.missiact1.msbooksorders.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unir.missiact1.msbooksorders.application.dtos.CartDto;
import com.unir.missiact1.msbooksorders.application.dtos.CartItemRequest;
import com.unir.missiact1.msbooksorders.application.dtos.catalogue.BookInfoDto;
import com.unir.missiact1.msbooksorders.application.request.mapster.CartMapper;
import com.unir.missiact1.msbooksorders.application.services.implementations.ICreateCartService;
import com.unir.missiact1.msbooksorders.commons.enums.ApiErrorCode;
import com.unir.missiact1.msbooksorders.commons.exceptions.CustomException;
import com.unir.missiact1.msbooksorders.domain.Cart;
import com.unir.missiact1.msbooksorders.domain.CartItem;
import com.unir.missiact1.msbooksorders.domain.interfaces.ICartRepository;
import com.unir.missiact1.msbooksorders.services.implementations.ICatalogueService;

@Service
public class CreateCartService implements ICreateCartService {
    private final ICartRepository cartRepo;
    private final ICatalogueService catalogue;

    public CreateCartService(ICartRepository cartRepo, ICatalogueService catalogue) {
        this.cartRepo = cartRepo;
        this.catalogue = catalogue;
    }

    @Override
    @Transactional
    public CartDto getOrCreateCart(UUID customerId) {
        Cart cart = cartRepo.findByCustomerId(customerId)
            .orElseGet(() -> cartRepo.save(new Cart(customerId)));
        return CartMapper.toDto(cart);
    }

    @Override
    @Transactional
    public CartDto addItem(UUID customerId, CartItemRequest req) {
        BookInfoDto book = catalogue.getBookById(req.getBookId());
        if (book.getPrice().compareTo(req.getUnitPrice()) != 0) {
            req.setUnitPrice(book.getPrice());
        }

        Cart cart = cartRepo.findByCustomerId(customerId)
            .orElseGet(() -> cartRepo.save(new Cart(customerId)));

        cart.getItems().stream()
            .filter(i -> i.getBookId().equals(req.getBookId()))
            .findFirst()
            .ifPresentOrElse(i -> {
                i.setQuantity(i.getQuantity() + req.getQuantity());
                i.setUnitPrice(req.getUnitPrice());
            }, () -> {
                CartItem item = new CartItem(req.getBookId(), req.getQuantity(), req.getUnitPrice());
                cart.addItem(item);
            });

        Cart saved = cartRepo.save(cart);
        return CartMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CartDto updateItem(UUID customerId, CartItemRequest req) {
        Cart cart = cartRepo.findByCustomerId(customerId)
            .orElseThrow(() -> new CustomException("Carrito no encontrado", ApiErrorCode.NotFound));

        CartItem item = cart.getItems().stream()
            .filter(i -> i.getBookId().equals(req.getBookId()))
            .findFirst()
            .orElseThrow(() -> new CustomException("Ítem no encontrado en el carrito", ApiErrorCode.NotFound));

        item.setQuantity(req.getQuantity());
        item.setUnitPrice(req.getUnitPrice());
        Cart updated = cartRepo.save(cart);
        return CartMapper.toDto(updated);
    }

    @Override
    @Transactional
    public CartDto removeItem(UUID customerId, UUID bookId) {
        Cart cart = cartRepo.findByCustomerId(customerId)
            .orElseThrow(() -> new CustomException("Carrito no encontrado", ApiErrorCode.NotFound));

        CartItem item = cart.getItems().stream()
            .filter(i -> i.getBookId().equals(bookId))
            .findFirst()
            .orElseThrow(() -> new CustomException("Ítem no encontrado en el carrito", ApiErrorCode.NotFound));

        cart.removeItem(item);
        Cart saved = cartRepo.save(cart);
        return CartMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void clearCart(UUID customerId) {
        Cart cart = cartRepo.findByCustomerId(customerId)
            .orElseThrow(() -> new CustomException("Carrito no encontrado", ApiErrorCode.NotFound));
        cart.getItems().clear();
        cartRepo.save(cart);
    }
}