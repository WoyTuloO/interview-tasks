package com.example.customer_order.adapter.output.persistence.order;// Spring-Data Repository

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataCustomerOrderRepository extends JpaRepository<CustomerOrderEntity, UUID> {}