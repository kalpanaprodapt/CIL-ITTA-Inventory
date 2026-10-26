# Route Target

Domain: **L2 and L3 Routing** (`switching`) - ASGI app `itta_api.domains.switching.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `asn` | `asn` | Asn | yes |
| `assigned_number` | `assignedNumber` | Assigned Number | yes |
| `rt_type` | `rtType` | Rt Type | no |
| `vrf_id` | `vrfId` | Vrf Id | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/route-targets` search
* `GET /api/v1/route-targets/meta` field catalogue
* `POST /api/v1/route-targets/reconcile?apply=false` reconcile external records
* `GET /api/v1/route-targets/export` CSV
