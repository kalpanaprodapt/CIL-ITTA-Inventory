# Ip Pool

Domain: **IP Management** (`ipam`) - ASGI app `itta_api.domains.ipam.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `subnet_id` | `subnetId` | Subnet Id | yes |
| `range_start` | `rangeStart` | Range Start | yes |
| `range_end` | `rangeEnd` | Range End | yes |
| `pool_type` | `poolType` | Pool Type | no |
| `lease_seconds` | `leaseSeconds` | Lease Seconds | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/ip-pools` search
* `GET /api/v1/ip-pools/meta` field catalogue
* `POST /api/v1/ip-pools/reconcile?apply=false` reconcile external records
* `GET /api/v1/ip-pools/export` CSV
