package com.example.customer_order.adapter.input.rest.order;

import com.example.customer_order.application.order.DeleteCustomerOrderCommand;
import com.example.customer_order.application.order.DeleteCustomerOrderHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class DeleteCustomerOrderController {

    private final DeleteCustomerOrderHandler handler;

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
        handler.handle(new DeleteCustomerOrderCommand(orderId));
        return ResponseEntity.noContent().build();
    }
}
