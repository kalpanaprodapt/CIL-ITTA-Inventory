# Runbook - IP Management

Owner: CIL ITTA inventory platform team · Escalation: `cil-itta-oncall`

## Services

| Workload | Kind | Port | Dashboard |
|----------|------|------|-----------|
| inventory-ipam-service | Spring Boot | 8080 | Grafana `itta-ipam` |
| inventory-ipam-api | FastAPI | 8000 | Grafana `itta-ipam` |

## Health checks

```
kubectl -n itta-inventory-prod get deploy inventory-ipam-service inventory-ipam-api
kubectl -n itta-inventory-prod logs deploy/inventory-ipam-service --tail=200
curl -fsS https://api-ipam.inventory.prod.cil.uniti.internal/healthz
```

## Record types

### Ip Subnet

* API: `/api/v1/ip-subnets` on `inventory-ipam-service`, facade on `inventory-ipam-api`
* Table: `inv_ip_subnet` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.ipam.ipsubnet`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh ipam IpSubnet`

### Ip Address

* API: `/api/v1/ip-addresses` on `inventory-ipam-service`, facade on `inventory-ipam-api`
* Table: `inv_ip_address` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.ipam.ipaddress`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh ipam IpAddress`

### Ip Pool

* API: `/api/v1/ip-pools` on `inventory-ipam-service`, facade on `inventory-ipam-api`
* Table: `inv_ip_pool` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.ipam.ippool`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh ipam IpPool`

### Vrf Instance

* API: `/api/v1/vrf-instances` on `inventory-ipam-service`, facade on `inventory-ipam-api`
* Table: `inv_vrf_instance` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.ipam.vrfinstance`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh ipam VrfInstance`

### Dns Record

* API: `/api/v1/dns-records` on `inventory-ipam-service`, facade on `inventory-ipam-api`
* Table: `inv_dns_record` (tenant scoped, unique `(tenant_id, code)`)
* Kafka topic: `itta.inventory.ipam.dnsrecord`
* Typical failure: duplicate `code` returns HTTP 409; stale `expectedVersion` returns HTTP 409
* Replay: re-publish from the table with `scripts/replay-events.sh ipam DnsRecord`

## Rollback

1. Identify the previous good tag in ACR: `az acr repository show-tags -n acruniticilitta --repository itta/inventory-ipam-service --orderby time_desc --top 5`
2. Update `helm/values/prod/inventory-ipam-service.yaml` and run `scripts/render.sh`
3. Merge to `release`; Argo CD syncs automatically (self-heal is off in prod, sync manually if needed)
