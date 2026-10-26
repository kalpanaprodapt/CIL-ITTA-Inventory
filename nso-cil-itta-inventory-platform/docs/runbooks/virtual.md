# Runbook - Virtual and Cloud

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-virtual-service | Spring Boot | 8080 | Grafana `itta-virtual` |
| inventory-virtual-api | FastAPI | 8000 | Grafana `itta-virtual` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-virtual-service inventory-virtual-api
kubectl -n itta-inventory-prod logs deploy/inventory-virtual-service --tail=200
curl -fsS https://api-virtual.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Vnf

* API: `/api/v1/vnfs` on `inventory-virtual-service`, facade on `inventory-virtual-api`
* Table: `inv_vnf` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.virtual.vnf`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh virtual Vnf`

### Hypervisor

* API: `/api/v1/hypervisors` on `inventory-virtual-service`, facade on `inventory-virtual-api`
* Table: `inv_hypervisor` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.virtual.hypervisor`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh virtual Hypervisor`

### Virtual Machine

* API: `/api/v1/virtual-machines` on `inventory-virtual-service`, facade on `inventory-virtual-api`
* Table: `inv_virtual_machine` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.virtual.virtualmachine`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh virtual VirtualMachine`

### Cloud Account

* API: `/api/v1/cloud-accounts` on `inventory-virtual-service`, facade on `inventory-virtual-api`
* Table: `inv_cloud_account` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.virtual.cloudaccount`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh virtual CloudAccount`

### K8s Cluster

* API: `/api/v1/k8s-clusters` on `inventory-virtual-service`, facade on `inventory-virtual-api`
* Table: `inv_k8s_cluster` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.virtual.k8scluster`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh virtual K8sCluster`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-virtual-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-virtual-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
