package com.example.customer_order.adapter.output.persistence.payment;

import com.example.customer_order.adapter.output.persistence.order.CustomerOrderEntity;
import com.example.customer_order.adapter.output.persistence.order.SpringDataCustomerOrderRepository;
import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.domain.model.order.OrderId;
import com.example.customer_order.domain.model.order.OrderStatus;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PayCustomerOrderRepositoryAdapter implements PayCustomerOrderRepositoryPort {

    private final SpringDataCustomerOrderRepository springDataRepository;

    public PayCustomerOrderRepositoryAdapter(SpringDataCustomerOrderRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Optional<CustomerOrder> findById(UUID id) {
        return springDataRepository.findById(id)
                .map(entity -> {
                    OrderId orderId = OrderId.of(entity.getId());
                    OrderStatus status = OrderStatus.valueOf(entity.getStatus().name());

                    return CustomerOrder.reconstruct(
                            orderId,
                            entity.getCustomerId(),
                            entity.getProductSku(),
                            entity.getQuantity(),
                            status
                    );
                });
    }

    @Override
    public void save(CustomerOrder order) {
        CustomerOrderEntity entity = springDataRepository.findById(order.getId().value())
                .orElseThrow(() -> new IllegalStateException("Nie znaleziono zamówienia o ID: " + order.getId().value()));

        entity.setStatus(order.getStatus());

        springDataRepository.save(entity);
    }
}