package com.example.manufacturing_order.adapter.input.rest.product;

import com.example.common.api.product.ProductAvailabilityResponse;
import com.example.manufacturing_order.application.product.CheckProductAvailabilityHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductAvailabilityController {

    private final CheckProductAvailabilityHandler handler;

    public ProductAvailabilityController(CheckProductAvailabilityHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/{productSku}/availability")
    public ProductAvailabilityResponse checkAvailability(@PathVariable String productSku) {
        return handler.handle(productSku);
    }
}
