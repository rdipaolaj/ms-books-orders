package com.unir.missiact1.msbooksorders.application.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public class CartItemDto {
    private Long id;
    private UUID bookId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private String title;
    private String coverImage;

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
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
}