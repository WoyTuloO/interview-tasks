package com.example.manufacturing_order.adapter.output.persistence.bom;

import com.example.manufacturing_order.domain.model.bom.BillOfMaterials;

import java.util.Optional;

public interface BillOfMaterialsRepositoryPort {
    Optional<BillOfMaterials> findByProductSku(String productSku);
}
