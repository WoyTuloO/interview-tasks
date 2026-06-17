package com.example.manufacturing_order.adapter.output.persistence.order;

import com.example.manufacturing_order.domain.model.order.ManufacturingOrder;
import com.example.manufacturing_order.domain.model.order.ManufacturingOrderId;
import com.example.manufacturing_order.domain.model.order.MaterialRequirement;

import java.util.Optional;
import java.util.UUID;

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

        for (MaterialRequirement requirement : domainOrder.getMaterialRequirements()) {
            MaterialRequirementEntity requirementEntity = new MaterialRequirementEntity();
            requirementEntity.setId(UUID.randomUUID());
            requirementEntity.setManufacturingOrder(entity);
            requirementEntity.setSemiProductSku(requirement.semiProductSku());
            requirementEntity.setRequiredQuantity(requirement.requiredQuantity());
            entity.getMaterialRequirements().add(requirementEntity);
        }

        repository.save(entity);
        return domainOrder;
    }

    @Override
    public Optional<ManufacturingOrder> findById(ManufacturingOrderId id) {
        return repository.findById(id.value()).map(this::toDomain);
    }

    @Override
    public Optional<ManufacturingOrder> findBySourceOrderId(String sourceOrderId) {
        return repository.findBySourceOrderId(sourceOrderId).map(this::toDomain);
    }

    private ManufacturingOrder toDomain(ManufacturingOrderEntity entity) {
        var requirements = entity.getMaterialRequirements().stream()
                .map(e -> new MaterialRequirement(e.getSemiProductSku(), e.getRequiredQuantity()))
                .toList();
        return ManufacturingOrder.reconstitute(
                entity.getId(),
                entity.getSourceOrderId(),
                entity.getProductSku(),
                entity.getQuantity(),
                requirements,
                entity.getStatus()
        );
    }
}
