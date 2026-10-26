# Runbook - Topology and Links

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-topology-service | Spring Boot | 8080 | Grafana `itta-topology` |
| inventory-topology-api | FastAPI | 8000 | Grafana `itta-topology` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-topology-service inventory-topology-api
kubectl -n itta-inventory-prod logs deploy/inventory-topology-service --tail=200
curl -fsS https://api-topology.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Link

* API: `/api/v1/links` on `inventory-topology-service`, facade on `inventory-topology-api`
* Table: `inv_link` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.topology.link`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh topology Link`

### Topology Node

* API: `/api/v1/topology-nodes` on `inventory-topology-service`, facade on `inventory-topology-api`
* Table: `inv_topology_node` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.topology.topologynode`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh topology TopologyNode`

### Topology Edge

* API: `/api/v1/topology-edges` on `inventory-topology-service`, facade on `inventory-topology-api`
* Table: `inv_topology_edge` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.topology.topologyedge`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh topology TopologyEdge`

### Topology View

* API: `/api/v1/topology-views` on `inventory-topology-service`, facade on `inventory-topology-api`
* Table: `inv_topology_view` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.topology.topologyview`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh topology TopologyView`

### Path Trace

* API: `/api/v1/path-traces` on `inventory-topology-service`, facade on `inventory-topology-api`
* Table: `inv_path_trace` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.topology.pathtrace`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh topology PathTrace`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-topology-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-topology-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
