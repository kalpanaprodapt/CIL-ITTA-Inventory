# Runbook - Vendor and Model

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-vendor-service | Spring Boot | 8080 | Grafana `itta-vendor` |
| inventory-vendor-api | FastAPI | 8000 | Grafana `itta-vendor` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-vendor-service inventory-vendor-api
kubectl -n itta-inventory-prod logs deploy/inventory-vendor-service --tail=200
curl -fsS https://api-vendor.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Vendor

* API: `/api/v1/vendors` on `inventory-vendor-service`, facade on `inventory-vendor-api`
* Table: `inv_vendor` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.vendor.vendor`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh vendor Vendor`

### Hardware Model

* API: `/api/v1/hardware-models` on `inventory-vendor-service`, facade on `inventory-vendor-api`
* Table: `inv_hardware_model` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.vendor.hardwaremodel`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh vendor HardwareModel`

### Sku

* API: `/api/v1/skus` on `inventory-vendor-service`, facade on `inventory-vendor-api`
* Table: `inv_sku` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.vendor.sku`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh vendor Sku`

### Support Contract

* API: `/api/v1/support-contracts` on `inventory-vendor-service`, facade on `inventory-vendor-api`
* Table: `inv_support_contract` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.vendor.supportcontract`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh vendor SupportContract`

### Warranty

* API: `/api/v1/warranties` on `inventory-vendor-service`, facade on `inventory-vendor-api`
* Table: `inv_warranty` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.vendor.warranty`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh vendor Warranty`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-vendor-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-vendor-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
