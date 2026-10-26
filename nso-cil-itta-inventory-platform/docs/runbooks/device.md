# Runbook - Network Device

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-device-service | Spring Boot | 8080 | Grafana `itta-device` |
| inventory-device-api | FastAPI | 8000 | Grafana `itta-device` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-device-service inventory-device-api
kubectl -n itta-inventory-prod logs deploy/inventory-device-service --tail=200
curl -fsS https://api-device.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Network Device

* API: `/api/v1/network-devices` on `inventory-device-service`, facade on `inventory-device-api`
* Table: `inv_network_device` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.device.networkdevice`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh device NetworkDevice`

### Chassis

* API: `/api/v1/chassises` on `inventory-device-service`, facade on `inventory-device-api`
* Table: `inv_chassis` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.device.chassis`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh device Chassis`

### Line Card

* API: `/api/v1/line-cards` on `inventory-device-service`, facade on `inventory-device-api`
* Table: `inv_line_card` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.device.linecard`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh device LineCard`

### Power Supply

* API: `/api/v1/power-supplies` on `inventory-device-service`, facade on `inventory-device-api`
* Table: `inv_power_supply` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.device.powersupply`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh device PowerSupply`

### Fan Tray

* API: `/api/v1/fan-traies` on `inventory-device-service`, facade on `inventory-device-api`
* Table: `inv_fan_tray` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.device.fantray`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh device FanTray`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-device-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-device-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
