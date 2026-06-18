package com.example.manufacturing_order.adapter.input.rest.order;

import com.example.manufacturing_order.application.order.CancelManufacturingOrderHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/manufacturing-orders")
public class CancelManufacturingOrderController {

    private final CancelManufacturingOrderHandler handler;

    public CancelManufacturingOrderController(CancelManufacturingOrderHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelProduction(@PathVariable("orderId") UUID orderId) {
        handler.handle(orderId);
        return ResponseEntity.noContent().build();
    }
}
