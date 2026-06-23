# Zadania rekrutacyjne

Repozytorium przedstawia uproszczony system zamówień zbudowany w stylu DDD/hexagonal architecture.

## Zadanie 1: Dodanie walidatora encji domenowych

Należy zaimplementować validator i wykorzystać go w poniższych metodach, jako zamiennik obecnej implementacji walidacji stanu zamówienia: 

- ManufacturingOrder.createNew()  
- CustomerOrder.updateDetails()

## Zadanie 2: Debug flow identyfikatorów zamówienia

W aplikacji występuje problem w procesie tworzenia oraz opłacania zamówień.

```http
POST /api/orders
```

```http
POST /api/orders/{id}/pay
```

Scenariusz biznesowy:

1. Klient tworzy zamówienie.
2. Klient opłaca zamówienie.
3. System tworzy powiązane zlecenie produkcyjne.

Aktualny objaw:
- Podczas tworzenia przychodzi błąd 'Serwis manufacturing jest niedostępny'
- Podczas opłacania zamówienia pojawia się exception

Wymagania:
- Należy przeanalizować proces opłacania zamówien i upewnić się, że zamówienie faktycznie zostaje opłacone i przyjęte do wytwarzania


## Zadanie 3: Implementacja endpointu GET

Frontend potrzebuje pobrać szczegóły pojedynczego zamówienia klienta po jego identyfikatorze razem ze statusem powiązanego zlecenia produkcyjnego.

Zaimplementuj endpoint:

```http
GET /api/orders/{orderId}
```

Parametry:

- `orderId` - identyfikator zamówienia, przekazywany w ścieżce URL.

Przykład requestu:

```http
GET /api/orders/5e5e6f3d-2b8d-45c7-a3a4-5b6d0f19b1c2
```

Endpoint powinien zwrócić dane zamówienia w formacie JSON:

```json
{
  "orderId": "5e5e6f3d-2b8d-45c7-a3a4-5b6d0f19b1c2",
  "customerId": "44b6e4c4-42d2-4c32-8b4e-efc4107f01a8",
  "productSku": "SKU-1",
  "quantity": 2,
  "status": "PAID",
  "manufacturingOrderStatus": "PENDING"
}
```

Pole `manufacturingOrderStatus` powinno zawierać status zlecenia produkcyjnego odpowiadającego wyszukiwanemu customer orderowi. Jeśli zlecenie produkcyjne jeszcze nie istnieje, zwróć `null`.

Oczekiwane zachowanie:

- Jeśli zamówienie istnieje, endpoint zwraca `200 OK` oraz dane zamówienia.
- Jeśli zamówienie nie istnieje, endpoint zwraca `404 Not Found`.
- Endpoint nie powinien modyfikować stanu zamówienia.