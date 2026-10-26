# Runbook - Optical

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-optical-service | Spring Boot | 8080 | Grafana `itta-optical` |
| inventory-optical-api | FastAPI | 8000 | Grafana `itta-optical` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-optical-service inventory-optical-api
kubectl -n itta-inventory-prod logs deploy/inventory-optical-service --tail=200
curl -fsS https://api-optical.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Optical Channel

* API: `/api/v1/optical-channels` on `inventory-optical-service`, facade on `inventory-optical-api`
* Table: `inv_optical_channel` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.optical.opticalchannel`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh optical OpticalChannel`

### Wdm System

* API: `/api/v1/wdm-systems` on `inventory-optical-service`, facade on `inventory-optical-api`
* Table: `inv_wdm_system` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.optical.wdmsystem`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh optical WdmSystem`

### Otn Link

* API: `/api/v1/otn-links` on `inventory-optical-service`, facade on `inventory-optical-api`
* Table: `inv_otn_link` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.optical.otnlink`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh optical OtnLink`

### Amplifier

* API: `/api/v1/amplifiers` on `inventory-optical-service`, facade on `inventory-optical-api`
* Table: `inv_amplifier` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.optical.amplifier`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh optical Amplifier`

### Roadm

* API: `/api/v1/roadms` on `inventory-optical-service`, facade on `inventory-optical-api`
* Table: `inv_roadm` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.optical.roadm`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh optical Roadm`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-optical-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-optical-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
