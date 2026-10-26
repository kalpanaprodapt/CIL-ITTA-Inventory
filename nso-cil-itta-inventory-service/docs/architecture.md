# Architecture

The inventory is split into 24 bounded contexts. Each context is a Spring Boot service with its own
Flyway history table (`flyway_<domain>_history`) but all share one PostgreSQL database (`itta_inventory`)
per environment, and one tenancy model.

```
UI (React) -> inventory-<domain>-api (FastAPI) -> inventory-<domain>-service (Spring Boot) -> PostgreSQL
                                                         |
                                                         +-> Kafka / Event Hubs  itta.inventory.<domain>.<entity>
```

## Layers inside a service

| Package | Responsibility |
|---------|----------------|
| `model` | JPA entities and enums |
| `dto` | Create / update / response / summary / search criteria |
| `mapper` | Explicit mapping between entities and DTOs |
| `repository` | Spring Data repositories |
| `spec` | JPA specifications built from search criteria |
| `validation` | Business rules beyond bean validation |
| `service` | Transactions, events, bulk operations, export |
| `event` | Domain event factories |
| `web` | REST controllers (OpenAPI annotated) |

## Cross cutting concerns

* **Tenancy**: `TenantFilter` reads `X-Tenant-Id`; every query is scoped by `tenant_id`.
* **Auditing**: `BaseEntity` stamps `created_*` / `updated_*` and a `@Version` column.
* **Errors**: `GlobalExceptionHandler` maps failures to a uniform `ApiError` payload.
* **Events**: published after commit by `KafkaEventPublisher`.
