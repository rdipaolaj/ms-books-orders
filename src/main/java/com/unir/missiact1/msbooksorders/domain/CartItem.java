package com.unir.missiact1.msbooksorders.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "book_id", length = 36, nullable = false)
    private UUID bookId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal subtotal;

    public CartItem() { }

    public CartItem(UUID bookId, Integer quantity, BigDecimal unitPrice) {
        this.bookId    = bookId;
        this.quantity  = quantity;
        this.unitPrice = unitPrice;
        recalc();
    }

    @PrePersist @PreUpdate
    private void recalc() {
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // Getters & Setters
    public Long getId() { return id; }
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
    public UUID getBookId() { return bookId; }
    public void setBookId(UUID bookId) { this.bookId = bookId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; recalc(); }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; recalc(); }
    public BigDecimal getSubtotal() { return subtotal; }
}