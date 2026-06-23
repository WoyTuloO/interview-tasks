package com.example.manufacturing_order.application.order;

import com.example.manufacturing_order.adapter.output.persistence.order.ManufacturingOrderRepositoryPort;
import com.example.manufacturing_order.domain.model.order.ManufacturingOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
public class CancelManufacturingOrderHandler {

    private final ManufacturingOrderRepositoryPort manufacturingOrderRepositoryPort;

    @Transactional
    public void handle(UUID sourceOrderId) {
        ManufacturingOrder order = manufacturingOrderRepositoryPort.findBySourceOrderId(sourceOrderId.toString())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nie znaleziono zlecenia produkcyjnego dla zamówienia: " + sourceOrderId));

        order.cancelProduction();
        manufacturingOrderRepositoryPort.save(order);
    }
}
