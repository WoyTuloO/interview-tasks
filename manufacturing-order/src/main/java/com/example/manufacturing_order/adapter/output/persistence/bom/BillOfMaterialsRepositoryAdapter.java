package com.example.manufacturing_order.adapter.output.persistence.bom;

import com.example.manufacturing_order.domain.model.bom.BillOfMaterials;
import com.example.manufacturing_order.domain.model.bom.BomLine;

import java.util.List;
import java.util.Optional;

public class BillOfMaterialsRepositoryAdapter implements BillOfMaterialsRepositoryPort {
    private final SpringDataProductBomLineRepository repository;

    public BillOfMaterialsRepositoryAdapter(SpringDataProductBomLineRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<BillOfMaterials> findByProductSku(String productSku) {
        List<ProductBomLineEntity> entities = repository.findByProductSku(productSku);
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        List<BomLine> lines = entities.stream()
                .map(e -> new BomLine(e.getSemiProductSku(), e.getQuantityPerUnit()))
                .toList();
        return Optional.of(new BillOfMaterials(productSku, lines));
    }
}
