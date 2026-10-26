# Runbook - Services

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-service-service | Spring Boot | 8080 | Grafana `itta-service` |
| inventory-service-api | FastAPI | 8000 | Grafana `itta-service` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-service-service inventory-service-api
kubectl -n itta-inventory-prod logs deploy/inventory-service-service --tail=200
curl -fsS https://api-service.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Service Catalog Item

* API: `/api/v1/service-catalog-items` on `inventory-service-service`, facade on `inventory-service-api`
* Table: `inv_service_catalog_item` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.service.servicecatalogitem`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh service ServiceCatalogItem`

### Service Instance

* API: `/api/v1/service-instances` on `inventory-service-service`, facade on `inventory-service-api`
* Table: `inv_service_instance` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.service.serviceinstance`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh service ServiceInstance`

### Service Order

* API: `/api/v1/service-orders` on `inventory-service-service`, facade on `inventory-service-api`
* Table: `inv_service_order` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.service.serviceorder`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh service ServiceOrder`

### Service Dependency

* API: `/api/v1/service-dependencies` on `inventory-service-service`, facade on `inventory-service-api`
* Table: `inv_service_dependency` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.service.servicedependency`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh service ServiceDependency`

### Service Endpoint

* API: `/api/v1/service-endpoints` on `inventory-service-service`, facade on `inventory-service-api`
* Table: `inv_service_endpoint` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.service.serviceendpoint`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh service ServiceEndpoint`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-service-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-service-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
