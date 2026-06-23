package com.example.manufacturing_order.adapter.output.persistence.order;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "manufacturing_order_material_requirements")
public class MaterialRequirementEntity {

    @Id
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturing_order_id", nullable = false)
    private ManufacturingOrderEntity manufacturingOrder;
    @Column(name = "semi_product_sku", nullable = false)
    private String semiProductSku;
    @Column(name = "required_quantity", nullable = false)
    private int requiredQuantity;

    public MaterialRequirementEntity() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public ManufacturingOrderEntity getManufacturingOrder() { return manufacturingOrder; }
    public void setManufacturingOrder(ManufacturingOrderEntity manufacturingOrder) { this.manufacturingOrder = manufacturingOrder; }
    public String getSemiProductSku() { return semiProductSku; }
    public void setSemiProductSku(String semiProductSku) { this.semiProductSku = semiProductSku; }
    public int getRequiredQuantity() { return requiredQuantity; }
    public void setRequiredQuantity(int requiredQuantity) { this.requiredQuantity = requiredQuantity; }
}
