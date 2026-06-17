package com.example.customer_order.adapter.output.persistence.order;

import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.domain.model.order.OrderId;
import com.example.customer_order.domain.model.order.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class DeleteCustomerOrderRepositoryAdapter implements DeleteCustomerOrderRepositoryPort {

    private final SpringDataCustomerOrderRepository springDataRepository;

    public DeleteCustomerOrderRepositoryAdapter(SpringDataCustomerOrderRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

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
    public void delete(CustomerOrder order) {
        springDataRepository.deleteById(order.getId().value());
    }
}
