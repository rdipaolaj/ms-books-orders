package com.unir.missiact1.msbooksorders.application.dtos;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class CartDto {
    private UUID id;
    private UUID customerId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<CartItemDto> items;
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<CartItemDto> getItems() { return items; }
    public void setItems(List<CartItemDto> items) { this.items = items; }
}