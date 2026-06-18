package com.example.customer_order.adapter.input.rest.cancel;

import com.example.customer_order.application.cancel.CancelOrderCommand;
import com.example.customer_order.application.cancel.CancelOrderHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class CancelOrderController {

    public final CancelOrderHandler cancelOrderHandler;

    public CancelOrderController(CancelOrderHandler cancelOrderHandler) {
        this.cancelOrderHandler = cancelOrderHandler;
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> handleCancel(@PathVariable("orderId") UUID orderId){
        CancelOrderCommand command = new CancelOrderCommand(orderId);

        cancelOrderHandler.handle(command);
        return ResponseEntity.ok().build();
    }
}
