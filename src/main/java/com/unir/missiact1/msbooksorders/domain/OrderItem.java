package com.unir.missiact1.msbooksorders.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal subtotal;

    public OrderItem() { }

    public OrderItem(UUID bookId, Integer quantity, BigDecimal unitPrice) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        recalcSubtotal();
    }

    @PrePersist
    @PreUpdate
    private void recalcSubtotal() {
        this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // Getters & Setters
    public Long getId() { return id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public UUID getBookId() { return bookId; }
    public void setBookId(UUID bookId) { this.bookId = bookId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; recalcSubtotal(); }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; recalcSubtotal(); }
    public BigDecimal getSubtotal() { return subtotal; }
}