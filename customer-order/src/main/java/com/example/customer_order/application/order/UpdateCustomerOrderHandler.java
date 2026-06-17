package com.example.customer_order.application.order;

import com.example.customer_order.adapter.output.manufacturing.ProductAvailabilityPort;
import com.example.customer_order.adapter.output.persistence.order.UpdateCustomerOrderRepositoryPort;
import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.domain.model.order.exception.ProductNotAvailableException;
import org.springframework.transaction.annotation.Transactional;

public class UpdateCustomerOrderHandler {

    private final UpdateCustomerOrderRepositoryPort repositoryPort;
    private final ProductAvailabilityPort productAvailabilityPort;

    public UpdateCustomerOrderHandler(
            UpdateCustomerOrderRepositoryPort repositoryPort,
            ProductAvailabilityPort productAvailabilityPort
    ) {
        this.repositoryPort = repositoryPort;
        this.productAvailabilityPort = productAvailabilityPort;
    }

    @Transactional
    public CustomerOrder handle(UpdateCustomerOrderCommand command) {
        CustomerOrder order = repositoryPort.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono zamówienia: " + command.orderId()));

        if (!productAvailabilityPort.isProductAvailable(command.productSku())) {
            throw new ProductNotAvailableException(command.productSku());
        }

        order.updateDetails(command.productSku(), command.quantity());

        repositoryPort.save(order);

        return order;
    }
}
