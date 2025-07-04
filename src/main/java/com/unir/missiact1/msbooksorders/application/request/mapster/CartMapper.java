package com.unir.missiact1.msbooksorders.application.request.mapster;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import com.unir.missiact1.msbooksorders.application.dtos.*;
import com.unir.missiact1.msbooksorders.domain.*;

public class CartMapper {

    public static CartDto toDto(Cart cart) {
        CartDto d = new CartDto();
        d.setId(cart.getId());
        d.setCustomerId(cart.getCustomerId());
        d.setCreatedAt(cart.getCreatedAt());
        d.setUpdatedAt(cart.getUpdatedAt());
        d.setItems(cart.getItems().stream().map(item -> {
            CartItemDto i = new CartItemDto();
            i.setId(item.getId());
            i.setBookId(item.getBookId());
            i.setQuantity(item.getQuantity());
            i.setUnitPrice(item.getUnitPrice());
            i.setSubtotal(item.getSubtotal());
            return i;
        }).collect(Collectors.toList()));
        BigDecimal total = d.getItems().stream()
                .map(CartItemDto::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        d.setTotal(total);
        return d;
    }
}