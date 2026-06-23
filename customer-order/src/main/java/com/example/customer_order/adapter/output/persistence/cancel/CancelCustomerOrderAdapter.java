package com.example.customer_order.adapter.output.persistence.cancel;

import com.example.customer_order.adapter.output.persistence.order.CustomerOrderEntity;
import com.example.customer_order.adapter.output.persistence.order.SpringDataCustomerOrderRepository;
import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.domain.model.order.OrderId;
import com.example.customer_order.domain.model.order.OrderStatus;

import com.example.customer_order.domain.model.order.exception.ManufacturingServiceUnavailableException;
import com.example.customer_order.domain.model.order.exception.OrderManufacturingStartedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CancelCustomerOrderAdapter implements CancelCustomerOrderPort{

    private final SpringDataCustomerOrderRepository springDataRepository;
    private final RestClient restClient;

    @Override
    public Optional<CustomerOrder> findById(UUID orderId) {
        return springDataRepository.findById(orderId)
                .map(entity -> CustomerOrder.reconstruct(
                        OrderId.of(entity.getId()),
                        entity.getCustomerId(),
                        entity.getProductSku(),
                        entity.getQuantity(),
                        OrderStatus.valueOf(entity.getStatus().name())
                ));
    }

    @Override
    public void cancel(UUID orderId) {
        CustomerOrderEntity entity = springDataRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Nie znaleziono zamówienia o ID: " + orderId));

        if(!entity.getStatus().equals(OrderStatus.PLACED)) {
            try {
                restClient.post()
                        .uri("/api/manufacturing-orders/{orderId}/cancel", orderId)
                        .retrieve()
                        .toBodilessEntity();
            } catch (RestClientResponseException exception) {
                if (exception.getStatusCode().isSameCodeAs(HttpStatus.CONFLICT)) {
                    throw new OrderManufacturingStartedException("Nie można anulować rozpoczętego zamówienia.");
                }
                throw new ManufacturingServiceUnavailableException(exception);
            }
        }
        entity.setStatus(OrderStatus.CANCEL);
        springDataRepository.save(entity);
    }
}
