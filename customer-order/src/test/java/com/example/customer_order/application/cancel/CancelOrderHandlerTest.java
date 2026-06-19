package com.example.customer_order.application.cancel;

import com.example.customer_order.adapter.output.persistence.cancel.CancelCustomerOrderPort;
import com.example.customer_order.domain.model.order.CustomerOrder;
import com.example.customer_order.domain.model.order.OrderId;
import com.example.customer_order.domain.model.order.OrderStatus;
import com.example.customer_order.domain.model.order.exception.CustomerOrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelOrderHandlerTest {

    @Mock
    private CancelCustomerOrderPort cancelCustomerOrderPort;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private CancelOrderHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CancelOrderHandler(cancelCustomerOrderPort, kafkaTemplate);
    }

    @Test
    void handle_shouldCancelOrder() {
        UUID orderId = UUID.randomUUID();
        CustomerOrder order = CustomerOrder.reconstruct(
                OrderId.of(orderId),
                UUID.randomUUID().toString(),
                "SKU-1",
                2,
                OrderStatus.PLACED
        );
        when(cancelCustomerOrderPort.findById(orderId)).thenReturn(Optional.of(order));

        handler.handle(new CancelOrderCommand(orderId));

        verify(cancelCustomerOrderPort).cancel(orderId);
    }

    @Test
    void handle_shouldThrowWhenOrderNotFound() {
        UUID orderId = UUID.randomUUID();
        when(cancelCustomerOrderPort.findById(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(new CancelOrderCommand(orderId)))
                .isInstanceOf(CustomerOrderNotFoundException.class);
    }
}
