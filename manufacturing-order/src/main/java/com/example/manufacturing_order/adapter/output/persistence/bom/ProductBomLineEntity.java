package com.example.manufacturing_order.adapter.output.persistence.bom;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "product_bom_lines",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_sku", "semi_product_sku"})
)
public class ProductBomLineEntity {
    @Id
    private UUID id;

    @Column(name = "product_sku", nullable = false)
    private String productSku;

    @Column(name = "semi_product_sku", nullable = false)
    private String semiProductSku;

    @Column(name = "quantity_per_unit", nullable = false)
    private int quantityPerUnit;

    public ProductBomLineEntity() {}

    public ProductBomLineEntity(UUID id, String productSku, String semiProductSku, int quantityPerUnit) {
        this.id = id;
        this.productSku = productSku;
        this.semiProductSku = semiProductSku;
        this.quantityPerUnit = quantityPerUnit;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getProductSku() { return productSku; }
    public void setProductSku(String productSku) { this.productSku = productSku; }
    public String getSemiProductSku() { return semiProductSku; }
    public void setSemiProductSku(String semiProductSku) { this.semiProductSku = semiProductSku; }
    public int getQuantityPerUnit() { return quantityPerUnit; }
    public void setQuantityPerUnit(int quantityPerUnit) { this.quantityPerUnit = quantityPerUnit; }
}
