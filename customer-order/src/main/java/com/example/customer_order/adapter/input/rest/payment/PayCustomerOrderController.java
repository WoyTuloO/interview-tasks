package com.example.customer_order.adapter.input.rest.payment;

import com.example.customer_order.application.payment.PayCustomerOrderCommand;
import com.example.customer_order.application.payment.PayCustomerOrderHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class PayCustomerOrderController {

    private final PayCustomerOrderHandler handler;

    public PayCustomerOrderController(PayCustomerOrderHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Void> handlePayment(@PathVariable UUID orderId) {

        var command = new PayCustomerOrderCommand(orderId);

        handler.handle(command);

        return ResponseEntity.ok().build();
    }
}