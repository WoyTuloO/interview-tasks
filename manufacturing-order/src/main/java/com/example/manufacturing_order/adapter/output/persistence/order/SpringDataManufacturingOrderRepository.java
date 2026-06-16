package com.example.manufacturing_order.adapter.output.persistence.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataManufacturingOrderRepository extends JpaRepository<ManufacturingOrderEntity, UUID> {
    Optional<ManufacturingOrderEntity> findBySourceOrderId(String sourceOrderId);
}
