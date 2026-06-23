package com.example.customer_order.adapter.input.rest.payment;

import com.example.customer_order.application.payment.PayCustomerOrderCommand;
import com.example.customer_order.application.payment.PayCustomerOrderHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class PayCustomerOrderController {

    private final PayCustomerOrderHandler handler;

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Void> handlePayment(@PathVariable UUID orderId) {
        PayCustomerOrderCommand command = new PayCustomerOrderCommand(orderId);
        handler.handle(command);
        return ResponseEntity.ok().build();
    }
}