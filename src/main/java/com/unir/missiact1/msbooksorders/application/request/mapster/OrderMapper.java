package com.unir.missiact1.msbooksorders.application.request.mapster;

import java.util.UUID;
import java.util.stream.Collectors;
import com.unir.missiact1.msbooksorders.application.dtos.*;
import com.unir.missiact1.msbooksorders.domain.*;

public class OrderMapper {
    public static Order toEntity(OrderCreateRequest r) {
        Order o = new Order(
            UUID.randomUUID().toString(),
            r.getCustomerId(),
            new Address(
                r.getShippingAddress().getStreet(),
                r.getShippingAddress().getCity(),
                r.getShippingAddress().getState(),
                r.getShippingAddress().getPostalCode(),
                r.getShippingAddress().getCountry()),
            new Address(
                r.getBillingAddress().getStreet(),
                r.getBillingAddress().getCity(),
                r.getBillingAddress().getState(),
                r.getBillingAddress().getPostalCode(),
                r.getBillingAddress().getCountry()),
            r.getShippingCost()
        );
        r.getItems().forEach(req -> o.addItem(
            new OrderItem(req.getBookId(), req.getQuantity(), req.getUnitPrice())
        ));
        return o;
    }

    public static OrderDto toDto(Order o) {
        OrderDto d = new OrderDto();
        d.setId(o.getId());
        d.setOrderNumber(o.getOrderNumber());
        d.setCustomerId(o.getCustomerId());
        d.setStatus(o.getStatus());
        d.setSubtotal(o.getSubtotal());
        d.setShippingCost(o.getShippingCost());
        d.setTotal(o.getTotal());
        d.setCreatedAt(o.getCreatedAt());
        d.setUpdatedAt(o.getUpdatedAt());
        d.setShippingAddress(toDto(o.getShippingAddress()));
        d.setBillingAddress(toDto(o.getBillingAddress()));
        d.setItems(o.getItems().stream().map(item -> {
            OrderItemDto i = new OrderItemDto();
            i.setId(item.getId());
            i.setBookId(item.getBookId());
            i.setQuantity(item.getQuantity());
            i.setUnitPrice(item.getUnitPrice());
            i.setSubtotal(item.getSubtotal());
            return i;
        }).collect(Collectors.toList()));
        return d;
    }

    private static AddressDto toDto(Address a) {
        AddressDto d = new AddressDto();
        d.setStreet(a.getStreet()); d.setCity(a.getCity());
        d.setState(a.getState()); d.setPostalCode(a.getPostalCode());
        d.setCountry(a.getCountry());
        return d;
    }
}