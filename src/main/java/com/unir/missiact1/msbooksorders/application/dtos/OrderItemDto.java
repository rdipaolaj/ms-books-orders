package com.unir.missiact1.msbooksorders.application.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemDto {
    private Long id;
    private UUID bookId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UUID getBookId() { return bookId; }
    public void setBookId(UUID bookId) { this.bookId = bookId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}