# Runbook - Circuits

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-circuit-service | Spring Boot | 8080 | Grafana `itta-circuit` |
| inventory-circuit-api | FastAPI | 8000 | Grafana `itta-circuit` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-circuit-service inventory-circuit-api
kubectl -n itta-inventory-prod logs deploy/inventory-circuit-service --tail=200
curl -fsS https://api-circuit.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Circuit

* API: `/api/v1/circuits` on `inventory-circuit-service`, facade on `inventory-circuit-api`
* Table: `inv_circuit` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.circuit.circuit`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh circuit Circuit`

### Circuit Segment

* API: `/api/v1/circuit-segments` on `inventory-circuit-service`, facade on `inventory-circuit-api`
* Table: `inv_circuit_segment` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.circuit.circuitsegment`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh circuit CircuitSegment`

### Circuit Provider

* API: `/api/v1/circuit-providers` on `inventory-circuit-service`, facade on `inventory-circuit-api`
* Table: `inv_circuit_provider` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.circuit.circuitprovider`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh circuit CircuitProvider`

### Circuit Termination

* API: `/api/v1/circuit-terminations` on `inventory-circuit-service`, facade on `inventory-circuit-api`
* Table: `inv_circuit_termination` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.circuit.circuittermination`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh circuit CircuitTermination`

### Circuit Order

* API: `/api/v1/circuit-orders` on `inventory-circuit-service`, facade on `inventory-circuit-api`
* Table: `inv_circuit_order` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.circuit.circuitorder`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh circuit CircuitOrder`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-circuit-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-circuit-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
