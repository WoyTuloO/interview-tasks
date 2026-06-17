package com.example.customer_order.config;

import com.example.customer_order.adapter.output.manufacturing.ProductAvailabilityPort;
import com.example.customer_order.adapter.output.outbox.SpringCustomerOrderEventPublisher;
import com.example.customer_order.adapter.output.persistence.order.CustomerOrderRepositoryPort;
import com.example.customer_order.adapter.output.persistence.order.DeleteCustomerOrderRepositoryPort;
import com.example.customer_order.adapter.output.persistence.order.UpdateCustomerOrderRepositoryPort;
import com.example.customer_order.adapter.output.persistence.payment.PayCustomerOrderRepositoryPort;
import com.example.customer_order.application.order.CreateCustomerOrderHandler;
import com.example.customer_order.application.order.UpdateCustomerOrderHandler;
import com.example.customer_order.application.payment.PayCustomerOrderHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class CustomerOrderConfig {

    @Bean
    public RestClient manufacturingRestClient(@Value("${manufacturing.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public CreateCustomerOrderHandler createCustomerOrderHandler(
            CustomerOrderRepositoryPort repositoryPort,
            ProductAvailabilityPort productAvailabilityPort) {
        return new CreateCustomerOrderHandler(repositoryPort, productAvailabilityPort);
    }

    @Bean
    public UpdateCustomerOrderHandler updateCustomerOrderHandler(
            UpdateCustomerOrderRepositoryPort repositoryPort,
            ProductAvailabilityPort productAvailabilityPort) {
        return new UpdateCustomerOrderHandler(repositoryPort, productAvailabilityPort);
    }

    @Bean
    public DeleteCustomerOrderHandler deleteCustomerOrderHandler(
            DeleteCustomerOrderRepositoryPort repositoryPort) {
        return new DeleteCustomerOrderHandler(repositoryPort);
    }

    @Bean
    public PayCustomerOrderHandler createPayCustomerOrderHandler(
            PayCustomerOrderRepositoryPort repositoryPort, SpringCustomerOrderEventPublisher publisher) {
        return new PayCustomerOrderHandler(repositoryPort, publisher);
    }
}