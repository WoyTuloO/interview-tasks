package com.example.customer_order.application.payment;

import com.example.customer_order.adapter.output.outbox.SpringCustomerOrderEventPublisher;
import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.adapter.output.persistence.payment.PayCustomerOrderRepositoryPort;
import com.example.customer_order.domain.model.order.exception.CustomerOrderNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class PayCustomerOrderHandler {

    private final PayCustomerOrderRepositoryPort repositoryPort;
    private final SpringCustomerOrderEventPublisher publisher;

    public PayCustomerOrderHandler(PayCustomerOrderRepositoryPort repositoryPort, SpringCustomerOrderEventPublisher publisher) {
        this.repositoryPort = repositoryPort;
        this.publisher = publisher;
    }

    @Transactional
    public void handle(PayCustomerOrderCommand command) {
        CustomerOrder order = repositoryPort.findById(command.orderId())
                .orElseThrow(() -> new CustomerOrderNotFoundException(command.orderId()));

        order.pay();

        repositoryPort.save(order);

        publisher.publishOrderPaid(order);
    }
}