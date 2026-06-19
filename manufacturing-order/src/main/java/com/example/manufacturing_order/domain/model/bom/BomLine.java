package com.example.manufacturing_order.domain.model.bom;

public record BomLine(String semiProductSku, int quantityPerUnit) {
    public BomLine {
        if (semiProductSku == null || semiProductSku.isBlank()) {
            throw new IllegalArgumentException("SKU półproduktu musi być określone.");
        }
        if (quantityPerUnit <= 0) {
            throw new IllegalArgumentException("Ilość na sztukę musi być większa od zera.");
        }
    }
}
