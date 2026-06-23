package com.example.customer_order.adapter.output.persistence.order;

import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.domain.model.order.OrderId;
import com.example.customer_order.domain.model.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UpdateCustomerOrderRepositoryAdapter implements UpdateCustomerOrderRepositoryPort {

    private final SpringDataCustomerOrderRepository springDataRepository;

    @Override
    public Optional<CustomerOrder> findById(UUID id) {
        return springDataRepository.findById(id)
                .map(entity -> CustomerOrder.reconstruct(
                        OrderId.of(entity.getId()),
                        entity.getCustomerId(),
                        entity.getProductSku(),
                        entity.getQuantity(),
                        OrderStatus.valueOf(entity.getStatus().name())
                ));
    }

    @Override
    public void save(CustomerOrder order) {
        CustomerOrderEntity entity = springDataRepository.findById(order.getId().value())
                .orElseThrow(() -> new IllegalStateException("Nie znaleziono zamówienia o ID: " + order.getId().value()));

        entity.setProductSku(order.getProductSku());
        entity.setQuantity(order.getQuantity());
        springDataRepository.save(entity);
    }
}
