package com.example.manufacturing_order.adapter.input.rest.order;

import com.example.manufacturing_order.application.order.StartManufacturingOrderHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manufacturing-orders")
@RequiredArgsConstructor
public class StartManufacturingOrderController {

    private final StartManufacturingOrderHandler handler;

    @PostMapping("/start")
    public ResponseEntity<Void> startProduction() {
        handler.handle();
        return ResponseEntity.noContent().build();
    }
}
