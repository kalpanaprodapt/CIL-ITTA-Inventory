# Lag Group

Domain: **Port and Interface** (`port`) - ASGI app `itta_api.domains.port.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `device_id` | `deviceId` | Device Id | yes |
| `lag_name` | `lagName` | Lag Name | yes |
| `member_count` | `memberCount` | Member Count | no |
| `lacp_mode` | `lacpMode` | Lacp Mode | no |
| `min_links` | `minLinks` | Min Links | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/lag-groups` search
* `GET /api/v1/lag-groups/meta` field catalogue
* `POST /api/v1/lag-groups/reconcile?apply=false` reconcile external records
* `GET /api/v1/lag-groups/export` CSV
