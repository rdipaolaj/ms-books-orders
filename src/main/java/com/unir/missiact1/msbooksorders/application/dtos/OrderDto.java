package com.unir.missiact1.msbooksorders.application.dtos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class OrderDto {
    private UUID id;
    private String orderNumber;
    private UUID customerId;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal shippingCost;
    private BigDecimal total;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private AddressDto shippingAddress;
    private AddressDto billingAddress;
    private List<OrderItemDto> items;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getShippingCost() { return shippingCost; }
    public void setShippingCost(BigDecimal shippingCost) { this.shippingCost = shippingCost; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }

    public AddressDto getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(AddressDto shippingAddress) { this.shippingAddress = shippingAddress; }

    public AddressDto getBillingAddress() { return billingAddress; }
    public void setBillingAddress(AddressDto billingAddress) { this.billingAddress = billingAddress; }

    public List<OrderItemDto> getItems() { return items; }
    public void setItems(List<OrderItemDto> items) { this.items = items; }
}
