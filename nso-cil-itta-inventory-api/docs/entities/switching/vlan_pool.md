# Vlan Pool

Domain: **L2 and L3 Routing** (`switching`) - ASGI app `itta_api.domains.switching.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `pool_name` | `poolName` | Pool Name | yes |
| `range_start` | `rangeStart` | Range Start | yes |
| `range_end` | `rangeEnd` | Range End | yes |
| `allocated` | `allocated` | Allocated | no |
| `scope` | `scope` | Scope | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/vlan-pools` search
* `GET /api/v1/vlan-pools/meta` field catalogue
* `POST /api/v1/vlan-pools/reconcile?apply=false` reconcile external records
* `GET /api/v1/vlan-pools/export` CSV
