package com.example.customer_order.application.cancel;

import com.example.customer_order.adapter.output.persistence.cancel.CancelCustomerOrderPort;
import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.domain.model.order.exception.CustomerOrderNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class CancelOrderHandler {

    private final CancelCustomerOrderPort cancelCustomerOrderPort;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    public void handle(CancelOrderCommand command) {

        CustomerOrder order = cancelCustomerOrderPort.findById(command.orderId())
                .orElseThrow(() -> new CustomerOrderNotFoundException(command.orderId()));

        cancelCustomerOrderPort.cancel(command.orderId());
        order.cancel();
    }
}
