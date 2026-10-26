# Vlan

Domain: **L2 and L3 Routing** (`switching`) - ASGI app `itta_api.domains.switching.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `vlan_id` | `vlanId` | Vlan Id | yes |
| `vlan_name` | `vlanName` | Vlan Name | yes |
| `vlan_type` | `vlanType` | Vlan Type | no |
| `pool_id` | `poolId` | Pool Id | no |
| `stp_mode` | `stpMode` | Stp Mode | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/vlans` search
* `GET /api/v1/vlans/meta` field catalogue
* `POST /api/v1/vlans/reconcile?apply=false` reconcile external records
* `GET /api/v1/vlans/export` CSV
