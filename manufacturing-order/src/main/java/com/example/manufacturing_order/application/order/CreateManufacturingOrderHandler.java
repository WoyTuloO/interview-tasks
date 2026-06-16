package com.example.manufacturing_order.application.order;

import com.example.manufacturing_order.adapter.output.persistence.order.ManufacturingOrderRepositoryPort;
import com.example.manufacturing_order.domain.model.order.ManufacturingOrder;
import org.springframework.transaction.annotation.Transactional;

public class CreateManufacturingOrderHandler {
    private final ManufacturingOrderRepositoryPort repositoryPort;

    public CreateManufacturingOrderHandler(ManufacturingOrderRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Transactional
    public void handle(CreateManufacturingOrderCommand command) {
        if (repositoryPort.findBySourceOrderId(command.sourceOrderId()).isPresent()) {
            return;
        }

        ManufacturingOrder manufacturingOrder = ManufacturingOrder.createNew(
                command.sourceOrderId(),
                command.productSku(),
                command.quantity()
        );

        repositoryPort.save(manufacturingOrder);
    }
}
