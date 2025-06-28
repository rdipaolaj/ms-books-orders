package com.unir.missiact1.msbooksorders.application.dtos;

import java.math.BigDecimal;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;

public class CartItemRequest {
    @NotNull private UUID bookId;
    @NotNull private Integer quantity;
    @NotNull private BigDecimal unitPrice;
    public UUID getBookId() { return bookId; }
    public void setBookId(UUID bookId) { this.bookId = bookId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}