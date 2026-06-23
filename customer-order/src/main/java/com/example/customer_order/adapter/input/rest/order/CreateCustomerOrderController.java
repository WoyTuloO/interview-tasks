package com.example.customer_order.adapter.input.rest.order;

import com.example.customer_order.application.order.CreateCustomerOrderCommand;
import com.example.customer_order.application.order.CreateCustomerOrderHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class CreateCustomerOrderController {

    private final CreateCustomerOrderHandler handler;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
        public String createOrder(@RequestBody CreateOrderRequest request) {
        var command = new CreateCustomerOrderCommand(
                request.customerId().toString(), request.productSku(), request.quantity()
        );
        return handler.handle(command).toString();
    }
}