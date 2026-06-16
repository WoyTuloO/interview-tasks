package com.example.customer_order.application.order;

import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.adapter.output.persistence.order.CustomerOrderRepositoryPort;
import com.example.customer_order.domain.model.order.OrderId;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

public class CreateCustomerOrderHandler {

    private final CustomerOrderRepositoryPort repositoryPort;

    public CreateCustomerOrderHandler(CustomerOrderRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Transactional
    public UUID handle(CreateCustomerOrderCommand command) {
        CustomerOrder order = CustomerOrder.createNew(
                OrderId.generate(),
                String.valueOf(command.customerId()),
                command.productSku(),
                command.quantity()
        );

        repositoryPort.save(order);

        return order.getId().value();
    }
}