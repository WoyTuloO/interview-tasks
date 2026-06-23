package com.example.manufacturing_order.domain.model.order;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ManufacturingOrder {
    private final ManufacturingOrderId id;
    private final String sourceOrderId;
    private final String productSku;
    private final int quantity;
    private final List<MaterialRequirement> materialRequirements;
    private ManufacturingStatus status;

    public static ManufacturingOrder createNew(
            String sourceOrderId,
            String productSku,
            int quantity,
            List<MaterialRequirement> materialRequirements
    ) {
        //Zad1
        if (sourceOrderId == null) {
            throw new IllegalArgumentException("Identyfikator źródłowy zamówienia nie może być pusty.");
        }
        if (productSku == null || productSku.isBlank()) {
            throw new IllegalArgumentException("SKU produktu musi być określone dla produkcji.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Ilość produkcyjna musi być większa od zera.");
        }
        if (materialRequirements == null || materialRequirements.isEmpty()) {
            throw new IllegalArgumentException("Zlecenie produkcyjne musi zawierać wymagania materiałowe.");
        }
        return new ManufacturingOrder(
                ManufacturingOrderId.generate(),
                sourceOrderId,
                productSku,
                quantity,
                materialRequirements,
                ManufacturingStatus.PENDING
        );
    }

    public static ManufacturingOrder reconstitute(
            UUID id,
            String sourceOrderId,
            String productSku,
            int quantity,
            List<MaterialRequirement> materialRequirements,
            ManufacturingStatus status
    ) {
        return new ManufacturingOrder(
                new ManufacturingOrderId(id),
                sourceOrderId,
                productSku,
                quantity,
                materialRequirements,
                status
        );
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

    public void cancelProduction() {
        if (this.status != ManufacturingStatus.PENDING) {
            throw new IllegalStateException("Nie można anulować produkcji o statusie: " + this.status);
        }
        this.status = ManufacturingStatus.CANCELED;
    }

    public void markAsNotified() {
        if (this.status != ManufacturingStatus.FINISHED) {
            throw new IllegalStateException("Można oznaczyć jako powiadomione tylko zlecenia zakończone.");
        }
        this.status = ManufacturingStatus.NOTIFIED;
    }

    public ManufacturingOrderId getId() { return id; }
    public String getSourceOrderId() { return sourceOrderId; }
    public String getProductSku() { return productSku; }
    public int getQuantity() { return quantity; }
    public List<MaterialRequirement> getMaterialRequirements() { return materialRequirements; }
    public ManufacturingStatus getStatus() { return status; }
}
