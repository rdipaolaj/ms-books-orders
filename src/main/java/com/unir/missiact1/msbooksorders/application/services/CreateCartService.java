package com.unir.missiact1.msbooksorders.application.services;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unir.missiact1.msbooksorders.application.dtos.CartDto;
import com.unir.missiact1.msbooksorders.application.dtos.CartItemRequest;
import com.unir.missiact1.msbooksorders.application.dtos.catalogue.BookInfoDto;
import com.unir.missiact1.msbooksorders.application.services.implementations.ICreateCartService;
import com.unir.missiact1.msbooksorders.infraestructure.repository.implementations.CartService;
import com.unir.missiact1.msbooksorders.services.implementations.ICatalogueService;

@Service
public class CreateCartService implements ICreateCartService {
    private final CartService cartService;
    private final ICatalogueService catalogue;

    public CreateCartService(CartService cartService,
            ICatalogueService catalogue) {
        this.cartService = cartService;
        this.catalogue = catalogue;
    }

    @Override
    @Transactional
    public CartDto getOrCreateCart(UUID customerId) {
        CartDto dto = cartService.getCart(customerId);
        return enrichWithBookData(dto);
    }

    @Override
    @Transactional
    public CartDto addItem(UUID customerId, CartItemRequest req) {
        BigDecimal price = catalogue.getBookById(req.getBookId()).getPrice();
        CartDto dto = cartService.addItem(customerId,
                req.getBookId(),
                req.getQuantity(),
                price);
        return enrichWithBookData(dto);
    }

    @Override
    @Transactional
    public CartDto updateItem(UUID customerId, CartItemRequest req) {
        BigDecimal price = catalogue.getBookById(req.getBookId()).getPrice();
        CartDto dto = cartService.updateItem(customerId,
                req.getBookId(),
                req.getQuantity(),
                price);
        return enrichWithBookData(dto);
    }

    @Override
    @Transactional
    public CartDto removeItem(UUID customerId, UUID bookId) {
        CartDto dto = cartService.removeItem(customerId, bookId);
        return enrichWithBookData(dto);
    }

    @Override
    @Transactional
    public void clearCart(UUID customerId) {
        cartService.clearCart(customerId);
    }

    @Override
    @Transactional
    public CartDto decrementItem(UUID customerId, UUID bookId) {
        CartDto dto = cartService.decrementItem(customerId, bookId);
        return enrichWithBookData(dto);
    }

    private CartDto enrichWithBookData(CartDto dto) {
        dto.getItems().forEach(item -> {
            BookInfoDto book = catalogue.getBookById(item.getBookId());
            item.setTitle(book.getTitle());
            item.setCoverImage(book.getCoverImage());
        });
        return dto;
    }
}