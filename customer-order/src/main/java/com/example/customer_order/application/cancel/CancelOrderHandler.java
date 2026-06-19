package com.example.customer_order.application.cancel;

import com.example.common.messaging.order.ManufacturingOrderKafkaTopics;
import com.example.customer_order.adapter.output.persistence.cancel.CancelCustomerOrderPort;
import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.domain.model.order.exception.CustomerOrderNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;

public class CancelOrderHandler {

    private final CancelCustomerOrderPort cancelCustomerOrderPort;
    private static final Logger log = LogManager.getLogger(CancelOrderHandler.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CancelOrderHandler(CancelCustomerOrderPort cancelCustomerOrderPort, KafkaTemplate<String, String> kafkaTemplate) {
        this.cancelCustomerOrderPort = cancelCustomerOrderPort;
        this.kafkaTemplate = kafkaTemplate;
    }
    @Transactional
    public void handle(CancelOrderCommand command) {

        CustomerOrder order = cancelCustomerOrderPort.findById(command.orderId())
                .orElseThrow(() -> new CustomerOrderNotFoundException(command.orderId()));

        cancelCustomerOrderPort.cancel(command.orderId());
        order.cancel();
    }
}
