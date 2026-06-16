package com.example.customer_order.application.order;

import com.example.customer_order.adapter.output.persistence.order.UpdateCustomerOrderRepositoryPort;
import com.example.customer_order.domain.model.order.CustomerOrder;
import org.springframework.transaction.annotation.Transactional;

public class UpdateCustomerOrderHandler {

    private final UpdateCustomerOrderRepositoryPort repositoryPort;

    public UpdateCustomerOrderHandler(UpdateCustomerOrderRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Transactional
    public CustomerOrder handle(UpdateCustomerOrderCommand command) {
        CustomerOrder order = repositoryPort.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono zamówienia: " + command.orderId()));

        order.updateDetails(command.productSku(), command.quantity());

        repositoryPort.save(order);

        return order;
    }
}
