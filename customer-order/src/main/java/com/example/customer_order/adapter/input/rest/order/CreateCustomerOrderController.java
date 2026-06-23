package com.example.customer_order.adapter.input.rest.order;

import com.example.customer_order.application.order.CreateCustomerOrderCommand;
import com.example.customer_order.application.order.CreateCustomerOrderHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class CreateCustomerOrderController {

    private final CreateCustomerOrderHandler handler;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
        var command = new CreateCustomerOrderCommand(
                request.customerId().toString(), request.productSku(), request.quantity()
        );

        UUID orderId = handler.handle(command);
        URI location = URI.create("/orders/" + orderId);

        return ResponseEntity
                .created(location)
                .body("Zamówienie zarejestrowane. ID: " + orderId + ". Oczekiwanie na płatność.");
    }
}