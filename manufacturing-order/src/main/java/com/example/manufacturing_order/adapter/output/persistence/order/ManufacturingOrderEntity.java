package com.example.manufacturing_order.adapter.output.persistence.order;

import com.example.manufacturing_order.domain.model.order.ManufacturingStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "manufacturing_orders")
public class ManufacturingOrderEntity {
    @Id
    private UUID id;

    @Column(name = "source_order_id", nullable = false, unique = true)
    private String sourceOrderId;

    @Column(name = "product_sku", nullable = false)
    private String productSku;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private ManufacturingStatus status;

    public ManufacturingOrderEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getSourceOrderId() { return sourceOrderId; }
    public void setSourceOrderId(String sourceOrderId) { this.sourceOrderId = sourceOrderId; }
    public String getProductSku() { return productSku; }
    public void setProductSku(String productSku) { this.productSku = productSku; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public ManufacturingStatus getStatus() { return status; }
    public void setStatus(ManufacturingStatus status) { this.status = status; }
}
