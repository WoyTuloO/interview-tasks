package com.example.manufacturing_order.config;

import com.example.manufacturing_order.adapter.output.persistence.order.ManufacturingOrderRepositoryAdapter;
import com.example.manufacturing_order.adapter.output.persistence.order.ManufacturingOrderRepositoryPort;
import com.example.manufacturing_order.adapter.output.persistence.order.SpringDataManufacturingOrderRepository;
import com.example.manufacturing_order.application.order.CreateManufacturingOrderHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManufacturingOrderConfig {

    @Bean
    public ManufacturingOrderRepositoryPort manufacturingOrderRepositoryPort(SpringDataManufacturingOrderRepository springDataRepository) {
        return new ManufacturingOrderRepositoryAdapter(springDataRepository);
    }

    @Bean
    public CreateManufacturingOrderHandler createManufacturingOrderHandler(ManufacturingOrderRepositoryPort repositoryPort) {
        return new CreateManufacturingOrderHandler(repositoryPort);
    }
}
