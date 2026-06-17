package com.example.customer_order.application.order;

import com.example.customer_order.adapter.output.manufacturing.ProductAvailabilityPort;
import com.example.customer_order.adapter.output.persistence.order.CustomerOrderRepositoryPort;
import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.domain.model.order.OrderId;
import com.example.customer_order.domain.model.order.exception.ProductNotAvailableException;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

public class CreateCustomerOrderHandler {

    private final CustomerOrderRepositoryPort repositoryPort;
    private final ProductAvailabilityPort productAvailabilityPort;

    public CreateCustomerOrderHandler(
            CustomerOrderRepositoryPort repositoryPort,
            ProductAvailabilityPort productAvailabilityPort
    ) {
        this.repositoryPort = repositoryPort;
        this.productAvailabilityPort = productAvailabilityPort;
    }

    @Transactional
    public UUID handle(CreateCustomerOrderCommand command) {
        if (!productAvailabilityPort.isProductAvailable(command.productSku())) {
            throw new ProductNotAvailableException(command.productSku());
        }

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