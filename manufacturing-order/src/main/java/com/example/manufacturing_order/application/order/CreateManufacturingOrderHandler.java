package com.example.manufacturing_order.application.order;

import com.example.manufacturing_order.adapter.output.persistence.bom.BillOfMaterialsRepositoryPort;
import com.example.manufacturing_order.adapter.output.persistence.order.ManufacturingOrderRepositoryPort;
import com.example.manufacturing_order.domain.model.bom.BillOfMaterials;
import com.example.manufacturing_order.domain.model.bom.BillOfMaterialsNotFoundException;
import com.example.manufacturing_order.domain.model.order.ManufacturingOrder;
import com.example.manufacturing_order.domain.model.order.MaterialRequirement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CreateManufacturingOrderHandler {
    private final ManufacturingOrderRepositoryPort repositoryPort;
    private final BillOfMaterialsRepositoryPort billOfMaterialsRepositoryPort;

    public CreateManufacturingOrderHandler(
            ManufacturingOrderRepositoryPort repositoryPort,
            BillOfMaterialsRepositoryPort billOfMaterialsRepositoryPort
    ) {
        this.repositoryPort = repositoryPort;
        this.billOfMaterialsRepositoryPort = billOfMaterialsRepositoryPort;
    }

    @Transactional
    public void handle(CreateManufacturingOrderCommand command) {
        if (repositoryPort.findBySourceOrderId(command.sourceOrderId()).isPresent()) {
            return;
        }

        BillOfMaterials billOfMaterials = billOfMaterialsRepositoryPort.findByProductSku(command.productSku())
                .orElseThrow(() -> new BillOfMaterialsNotFoundException(command.productSku()));

        List<MaterialRequirement> materialRequirements = billOfMaterials.calculateRequirements(command.quantity());

        ManufacturingOrder manufacturingOrder = ManufacturingOrder.createNew(
                command.sourceOrderId(),
                command.productSku(),
                command.quantity(),
                materialRequirements
        );

        repositoryPort.save(manufacturingOrder);
    }
}
