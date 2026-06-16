package com.example.manufacturing_order.adapter.output.persistence.order;

import com.example.manufacturing_order.domain.model.order.ManufacturingOrder;
import com.example.manufacturing_order.domain.model.order.ManufacturingOrderId;

import java.util.Optional;

public class ManufacturingOrderRepositoryAdapter implements ManufacturingOrderRepositoryPort {
    private final SpringDataManufacturingOrderRepository repository;

    public ManufacturingOrderRepositoryAdapter(SpringDataManufacturingOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public ManufacturingOrder save(ManufacturingOrder domainOrder) {
        ManufacturingOrderEntity entity = new ManufacturingOrderEntity();
        entity.setId(domainOrder.getId().value());
        entity.setSourceOrderId(domainOrder.getSourceOrderId());
        entity.setProductSku(domainOrder.getProductSku());
        entity.setQuantity(domainOrder.getQuantity());
        entity.setStatus(domainOrder.getStatus());

        repository.save(entity);
        return domainOrder;
    }

    @Override
    public Optional<ManufacturingOrder> findById(ManufacturingOrderId id) {
        return repository.findById(id.value())
                .map(e -> ManufacturingOrder.reconstitute(e.getId(), e.getSourceOrderId(), e.getProductSku(), e.getQuantity(), e.getStatus()));
    }

    @Override
    public Optional<ManufacturingOrder> findBySourceOrderId(String sourceOrderId) {
        return repository.findBySourceOrderId(sourceOrderId)
                .map(e -> ManufacturingOrder.reconstitute(e.getId(), e.getSourceOrderId(), e.getProductSku(), e.getQuantity(), e.getStatus()));
    }
}
