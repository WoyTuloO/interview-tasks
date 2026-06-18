package com.example.manufacturing_order.adapter.input.scheduler;

import com.example.manufacturing_order.application.order.CheckManufacturingOrderStatusHandler;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ManufacturingOrderScheduler {

    private static final Logger log = LoggerFactory.getLogger(ManufacturingOrderScheduler.class);
    private final CheckManufacturingOrderStatusHandler checkManufacturingOrderStatusHandler;

    public ManufacturingOrderScheduler(CheckManufacturingOrderStatusHandler checkManufacturingOrderStatusHandler) {
        this.checkManufacturingOrderStatusHandler = checkManufacturingOrderStatusHandler;
    }

    @Scheduled(fixedRate = 60_000)
    public void processFinishedManufacturingOrders() {
        try {
            checkManufacturingOrderStatusHandler.handle();
        } catch (Exception exception) {
            log.error("Błąd podczas przetwarzania zakończonych zleceń produkcyjnych: {}", exception.getMessage());
        }
    }
}
