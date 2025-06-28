package com.unir.missiact1.msbooksorders.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue
	@UuidGenerator
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(name = "id", length = 36, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "customer_id", length = 36, nullable = false)
    private UUID customerId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(name = "shipping_cost", nullable = false)
    private BigDecimal shippingCost;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",       column = @Column(name = "shipping_street")),
        @AttributeOverride(name = "city",         column = @Column(name = "shipping_city")),
        @AttributeOverride(name = "state",        column = @Column(name = "shipping_state")),
        @AttributeOverride(name = "postalCode",   column = @Column(name = "shipping_postal_code")),
        @AttributeOverride(name = "country",      column = @Column(name = "shipping_country"))
    })
    private Address shippingAddress;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street",       column = @Column(name = "billing_street")),
        @AttributeOverride(name = "city",         column = @Column(name = "billing_city")),
        @AttributeOverride(name = "state",        column = @Column(name = "billing_state")),
        @AttributeOverride(name = "postalCode",   column = @Column(name = "billing_postal_code")),
        @AttributeOverride(name = "country",      column = @Column(name = "billing_country"))
    })
    private Address billingAddress;

    public Order() { }

    // Business constructor
    public Order(String orderNumber,
                 UUID customerId,
                 Address shippingAddress,
                 Address billingAddress,
                 BigDecimal shippingCost) {
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.status = "CREATED";
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.shippingCost = shippingCost;
        this.subtotal = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
        recalcTotals();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
        recalcTotals();
    }

    // Add and remove
    public void addItem(OrderItem item) {
        item.setOrder(this);
        items.add(item);
        recalcTotals();
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
        recalcTotals();
    }

    private void recalcTotals() {
        this.subtotal = items.stream()
            .map(OrderItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.total = this.subtotal.add(this.shippingCost != null ? this.shippingCost : BigDecimal.ZERO);
    }

    // Getters & Setters
    public UUID getId() { return id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getShippingCost() { return shippingCost; }
    public void setShippingCost(BigDecimal shippingCost) { this.shippingCost = shippingCost; recalcTotals(); }
    public BigDecimal getTotal() { return total; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public List<OrderItem> getItems() { return items; }
    public Address getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(Address shippingAddress) { this.shippingAddress = shippingAddress; }
    public Address getBillingAddress() { return billingAddress; }
    public void setBillingAddress(Address billingAddress) { this.billingAddress = billingAddress; }
}
