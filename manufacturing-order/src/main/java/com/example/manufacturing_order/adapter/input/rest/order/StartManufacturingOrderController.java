package com.example.manufacturing_order.adapter.input.rest.order;

import com.example.manufacturing_order.application.order.StartManufacturingOrderHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manufacturing-orders")
public class StartManufacturingOrderController {

    private final StartManufacturingOrderHandler handler;

    public StartManufacturingOrderController(StartManufacturingOrderHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/start")
    public ResponseEntity<Void> startProduction() {
        handler.handle();
        return ResponseEntity.noContent().build();
    }
}
