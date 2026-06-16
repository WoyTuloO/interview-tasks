package com.example.manufacturing_order.domain.model.order;

import java.util.UUID;

public class ManufacturingOrder {
    private final ManufacturingOrderId id;
    private final String sourceOrderId;
    private final String productSku;
    private final int quantity;
    private ManufacturingStatus status;

    private ManufacturingOrder(ManufacturingOrderId id, String sourceOrderId, String productSku, int quantity, ManufacturingStatus status) {
        this.id = id;
        this.sourceOrderId = sourceOrderId;
        this.productSku = productSku;
        this.quantity = quantity;
        this.status = status;
    }

    public static ManufacturingOrder createNew(String sourceOrderId, String productSku, int quantity) {
        if (sourceOrderId == null) {
            throw new IllegalArgumentException("Identyfikator źródłowy zamówienia nie może być pusty.");
        }
        if (productSku == null || productSku.isBlank()) {
            throw new IllegalArgumentException("SKU produktu musi być określone dla produkcji.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Ilość produkcyjna musi być większa od zera.");
        }
        return new ManufacturingOrder(ManufacturingOrderId.generate(), sourceOrderId, productSku, quantity, ManufacturingStatus.PENDING);
    }

    public static ManufacturingOrder reconstitute(UUID id, String sourceOrderId, String productSku, int quantity, ManufacturingStatus status) {
        return new ManufacturingOrder(new ManufacturingOrderId(id), sourceOrderId, productSku, quantity, status);
    }

    public void startProduction() {
        if (this.status != ManufacturingStatus.PENDING) {
            throw new IllegalStateException("Można uruchomić produkcję tylko dla zleceń oczekujących.");
        }
        this.status = ManufacturingStatus.IN_PROGRESS;
    }

    public void finishProduction() {
        if (this.status != ManufacturingStatus.IN_PROGRESS) {
            throw new IllegalStateException("Nie można zakończyć produkcji, która nie wystartowała.");
        }
        this.status = ManufacturingStatus.FINISHED;
    }

    public ManufacturingOrderId getId() { return id; }
    public String getSourceOrderId() { return sourceOrderId; }
    public String getProductSku() { return productSku; }
    public int getQuantity() { return quantity; }
    public ManufacturingStatus getStatus() { return status; }
}
