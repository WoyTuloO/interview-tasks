package com.example.customer_order.config;

import com.example.customer_order.adapter.output.outbox.SpringCustomerOrderEventPublisher;
import com.example.customer_order.application.order.CreateCustomerOrderHandler;
import com.example.customer_order.application.order.UpdateCustomerOrderHandler;
import com.example.customer_order.application.payment.PayCustomerOrderHandler;
import com.example.customer_order.adapter.output.persistence.order.CustomerOrderRepositoryPort;
import com.example.customer_order.adapter.output.persistence.order.UpdateCustomerOrderRepositoryPort;
import com.example.customer_order.adapter.output.persistence.payment.PayCustomerOrderRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerOrderConfig {

    @Bean
    public CreateCustomerOrderHandler createCustomerOrderHandler(
            CustomerOrderRepositoryPort repositoryPort) {
        return new CreateCustomerOrderHandler(repositoryPort);
    }

    @Bean
    public UpdateCustomerOrderHandler updateCustomerOrderHandler(
            UpdateCustomerOrderRepositoryPort repositoryPort) {
        return new UpdateCustomerOrderHandler(repositoryPort);
    }

    @Bean
    public PayCustomerOrderHandler createPayCustomerOrderHandler(
            PayCustomerOrderRepositoryPort repositoryPort, SpringCustomerOrderEventPublisher publisher) {
        return new PayCustomerOrderHandler(repositoryPort, publisher);
    }
}