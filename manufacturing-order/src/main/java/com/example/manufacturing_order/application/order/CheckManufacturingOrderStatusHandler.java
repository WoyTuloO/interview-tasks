package com.example.manufacturing_order.application.order;

import com.example.manufacturing_order.adapter.output.messaging.order.ManufacturingOrderFinishedEventPublisherPort;
import com.example.manufacturing_order.adapter.output.persistence.order.ManufacturingOrderRepositoryPort;
import com.example.manufacturing_order.domain.model.order.ManufacturingOrder;
import org.springframework.transaction.annotation.Transactional;

public class CheckManufacturingOrderStatusHandler {

    private final ManufacturingOrderRepositoryPort manufacturingOrderRepositoryPort;
    private final ManufacturingOrderFinishedEventPublisherPort eventPublisherPort;

    public CheckManufacturingOrderStatusHandler(
            ManufacturingOrderRepositoryPort manufacturingOrderRepositoryPort,
            ManufacturingOrderFinishedEventPublisherPort eventPublisherPort
    ) {
        this.manufacturingOrderRepositoryPort = manufacturingOrderRepositoryPort;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Transactional
    public void handle() {
        manufacturingOrderRepositoryPort.findFirstFinished()
                .ifPresent(this::publishAndMarkAsNotified);
    }

    private void publishAndMarkAsNotified(ManufacturingOrder order) {
        eventPublisherPort.publish(order);
        order.markAsNotified();
        manufacturingOrderRepositoryPort.save(order);
    }
}
