# Runbook - MPLS and VPN

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-mpls-service | Spring Boot | 8080 | Grafana `itta-mpls` |
| inventory-mpls-api | FastAPI | 8000 | Grafana `itta-mpls` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-mpls-service inventory-mpls-api
kubectl -n itta-inventory-prod logs deploy/inventory-mpls-service --tail=200
curl -fsS https://api-mpls.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Mpls Lsp

* API: `/api/v1/mpls-lsps` on `inventory-mpls-service`, facade on `inventory-mpls-api`
* Table: `inv_mpls_lsp` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.mpls.mplslsp`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh mpls MplsLsp`

### Vpn Service

* API: `/api/v1/vpn-services` on `inventory-mpls-service`, facade on `inventory-mpls-api`
* Table: `inv_vpn_service` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.mpls.vpnservice`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh mpls VpnService`

### Pseudo Wire

* API: `/api/v1/pseudo-wires` on `inventory-mpls-service`, facade on `inventory-mpls-api`
* Table: `inv_pseudo_wire` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.mpls.pseudowire`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh mpls PseudoWire`

### Tunnel Endpoint

* API: `/api/v1/tunnel-endpoints` on `inventory-mpls-service`, facade on `inventory-mpls-api`
* Table: `inv_tunnel_endpoint` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.mpls.tunnelendpoint`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh mpls TunnelEndpoint`

### Qos Policy

* API: `/api/v1/qos-policies` on `inventory-mpls-service`, facade on `inventory-mpls-api`
* Table: `inv_qos_policy` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.mpls.qospolicy`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh mpls QosPolicy`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-mpls-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-mpls-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
