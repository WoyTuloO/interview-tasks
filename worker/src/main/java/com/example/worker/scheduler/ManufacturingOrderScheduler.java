package com.example.worker.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ManufacturingOrderScheduler {

    private static final Logger log = LoggerFactory.getLogger(ManufacturingOrderScheduler.class);
    private final RestClient restClient;

    // Wstrzykujemy adres URL z pliku konfiguracyjnego i inicjalizujemy RestClient
    public ManufacturingOrderScheduler(@Value("${manufacturing.base-url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Scheduled(fixedRate = 600000, initialDelay = 5000)
    public void triggerManufacturingOrderStart() {
        log.info("Wysyłanie żądania POST do StartManufacturingOrderController...");

        try {
            // Odbieramy ResponseEntity<Void>, ponieważ kontroler zwraca noContent() (204)
            ResponseEntity<Void> response = restClient.post()
                    .uri("/api/manufacturing-orders/start")
                    .retrieve()
                    .toEntity(Void.class);

            // Sprawdzamy czy kod statusu to 2xx (dla 204 No Content to prawda)
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Zamówienia produkcyjne zostały pomyślnie uruchomione (HTTP {}).", response.getStatusCode());
            } else {
                log.warn("Otrzymano nieoczekiwany kod statusu: {}", response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Błąd podczas wywoływania handlera zamówień: {}", e.getMessage());
        }
    }
}