# Runbook - Customers and SLA

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-customer-service | Spring Boot | 8080 | Grafana `itta-customer` |
| inventory-customer-api | FastAPI | 8000 | Grafana `itta-customer` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-customer-service inventory-customer-api
kubectl -n itta-inventory-prod logs deploy/inventory-customer-service --tail=200
curl -fsS https://api-customer.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Customer

* API: `/api/v1/customers` on `inventory-customer-service`, facade on `inventory-customer-api`
* Table: `inv_customer` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.customer.customer`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh customer Customer`

### Customer Account

* API: `/api/v1/customer-accounts` on `inventory-customer-service`, facade on `inventory-customer-api`
* Table: `inv_customer_account` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.customer.customeraccount`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh customer CustomerAccount`

### Customer Contact

* API: `/api/v1/customer-contacts` on `inventory-customer-service`, facade on `inventory-customer-api`
* Table: `inv_customer_contact` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.customer.customercontact`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh customer CustomerContact`

### Contract

* API: `/api/v1/contracts` on `inventory-customer-service`, facade on `inventory-customer-api`
* Table: `inv_contract` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.customer.contract`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh customer Contract`

### Sla Profile

* API: `/api/v1/sla-profiles` on `inventory-customer-service`, facade on `inventory-customer-api`
* Table: `inv_sla_profile` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.customer.slaprofile`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh customer SlaProfile`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-customer-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-customer-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
