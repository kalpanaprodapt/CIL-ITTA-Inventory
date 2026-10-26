# Runbook - Discovery and Reconciliation

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-discovery-service | Spring Boot | 8080 | Grafana `itta-discovery` |
| inventory-discovery-api | FastAPI | 8000 | Grafana `itta-discovery` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-discovery-service inventory-discovery-api
kubectl -n itta-inventory-prod logs deploy/inventory-discovery-service --tail=200
curl -fsS https://api-discovery.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Discovery Job

* API: `/api/v1/discovery-jobs` on `inventory-discovery-service`, facade on `inventory-discovery-api`
* Table: `inv_discovery_job` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.discovery.discoveryjob`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh discovery DiscoveryJob`

### Discovery Profile

* API: `/api/v1/discovery-profiles` on `inventory-discovery-service`, facade on `inventory-discovery-api`
* Table: `inv_discovery_profile` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.discovery.discoveryprofile`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh discovery DiscoveryProfile`

### Discovered Asset

* API: `/api/v1/discovered-assets` on `inventory-discovery-service`, facade on `inventory-discovery-api`
* Table: `inv_discovered_asset` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.discovery.discoveredasset`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh discovery DiscoveredAsset`

### Reconciliation Rule

* API: `/api/v1/reconciliation-rules` on `inventory-discovery-service`, facade on `inventory-discovery-api`
* Table: `inv_reconciliation_rule` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.discovery.reconciliationrule`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh discovery ReconciliationRule`

### Reconciliation Result

* API: `/api/v1/reconciliation-results` on `inventory-discovery-service`, facade on `inventory-discovery-api`
* Table: `inv_reconciliation_result` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.discovery.reconciliationresult`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh discovery ReconciliationResult`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-discovery-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-discovery-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
