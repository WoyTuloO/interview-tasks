package com.example.manufacturing_order.adapter.input.rest.product;

import com.example.common.api.product.ProductAvailabilityResponse;
import com.example.manufacturing_order.application.product.CheckProductAvailabilityPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductAvailabilityController {

    private final CheckProductAvailabilityPort checkProductAvailabilityPort;

    @GetMapping("/{productSku}/availability")
    public ProductAvailabilityResponse checkAvailability(@PathVariable String productSku) {
        return checkProductAvailabilityPort.check(productSku);
    }
}
