package com.example.manufacturing_order.adapter.output.persistence.stock;

import com.example.manufacturing_order.domain.model.stock.MaterialStock;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MaterialStockRepositoryAdapter implements MaterialStockRepositoryPort {
    private final SpringDataMaterialStockRepository repository;

    public MaterialStockRepositoryAdapter(SpringDataMaterialStockRepository repository) {
        this.repository = repository;
    }

    @Override
    public MaterialStock save(MaterialStock materialStock) {
        MaterialStockEntity entity = repository.findById(materialStock.getMaterialSku())
                .orElseGet(MaterialStockEntity::new);
        entity.setMaterialSku(materialStock.getMaterialSku());
        entity.setQuantity(materialStock.getQuantity());
        repository.save(entity);
        return materialStock;
    }

    @Override
    public Optional<MaterialStock> findByMaterialSku(String materialSku) {
        return repository.findById(materialSku).map(this::toDomain);
    }

    @Override
    public List<MaterialStock> findByMaterialSkus(Collection<String> materialSkus) {
        return repository.findByMaterialSkuIn(materialSkus).stream()
                .map(this::toDomain)
                .toList();
    }

    private MaterialStock toDomain(MaterialStockEntity entity) {
        return MaterialStock.reconstitute(entity.getMaterialSku(), entity.getQuantity());
    }
}
