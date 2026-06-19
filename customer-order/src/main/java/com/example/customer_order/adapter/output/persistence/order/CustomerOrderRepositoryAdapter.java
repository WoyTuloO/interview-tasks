package com.example.customer_order.adapter.output.persistence.order;

import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.domain.model.order.OrderId;
import com.example.customer_order.domain.model.order.OrderStatus;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CustomerOrderRepositoryAdapter implements CustomerOrderRepositoryPort {

    private final SpringDataCustomerOrderRepository repository;

    public CustomerOrderRepositoryAdapter(SpringDataCustomerOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerOrder save(CustomerOrder domainOrder) {
        CustomerOrderEntity entity = new CustomerOrderEntity();
        entity.setId(domainOrder.getId().value());
        entity.setCustomerId(domainOrder.getCustomerId());
        entity.setProductSku(domainOrder.getProductSku());
        entity.setQuantity(domainOrder.getQuantity());
        entity.setStatus(domainOrder.getStatus());

        repository.save(entity);
        return domainOrder;
    }

    @Override
    public Optional<CustomerOrder> findById(OrderId id) {
        return repository.findById(id.value())
                .map(e -> new CustomerOrder(
                        new OrderId(e.getId()), e.getCustomerId(), e.getProductSku(), e.getQuantity(),
                       OrderStatus.valueOf(e.getStatus().toString())
                ));
    }
}