package com.example.manufacturing_order.adapter.output.persistence.stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "material_stock")
public class MaterialStockEntity {

    @Id
    @Column(name = "material_sku", nullable = false)
    private String materialSku;
    @Column(nullable = false)
    private int quantity;

    public MaterialStockEntity() {}

    public MaterialStockEntity(String materialSku, int quantity) {
        this.materialSku = materialSku;
        this.quantity = quantity;
    }

    public String getMaterialSku() { return materialSku; }
    public void setMaterialSku(String materialSku) { this.materialSku = materialSku; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
