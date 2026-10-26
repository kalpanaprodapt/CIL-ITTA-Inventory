# Runbook - L2 and L3 Routing

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-switching-service | Spring Boot | 8080 | Grafana `itta-switching` |
| inventory-switching-api | FastAPI | 8000 | Grafana `itta-switching` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-switching-service inventory-switching-api
kubectl -n itta-inventory-prod logs deploy/inventory-switching-service --tail=200
curl -fsS https://api-switching.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Vlan

* API: `/api/v1/vlans` on `inventory-switching-service`, facade on `inventory-switching-api`
* Table: `inv_vlan` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.switching.vlan`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh switching Vlan`

### Vlan Pool

* API: `/api/v1/vlan-pools` on `inventory-switching-service`, facade on `inventory-switching-api`
* Table: `inv_vlan_pool` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.switching.vlanpool`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh switching VlanPool`

### Route Target

* API: `/api/v1/route-targets` on `inventory-switching-service`, facade on `inventory-switching-api`
* Table: `inv_route_target` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.switching.routetarget`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh switching RouteTarget`

### Bgp Session

* API: `/api/v1/bgp-sessions` on `inventory-switching-service`, facade on `inventory-switching-api`
* Table: `inv_bgp_session` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.switching.bgpsession`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh switching BgpSession`

### Ospf Area

* API: `/api/v1/ospf-areas` on `inventory-switching-service`, facade on `inventory-switching-api`
* Table: `inv_ospf_area` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.switching.ospfarea`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh switching OspfArea`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-switching-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-switching-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
