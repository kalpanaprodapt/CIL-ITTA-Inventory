# Patch Panel

Domain: **Cabling** (`cabling`) - ASGI app `itta_api.domains.cabling.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `room_id` | `roomId` | Room Id | yes |
| `rack_id` | `rackId` | Rack Id | no |
| `panel_type` | `panelType` | Panel Type | no |
| `port_count` | `portCount` | Port Count | yes |
| `label` | `label` | Label | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/patch-panels` search
* `GET /api/v1/patch-panels/meta` field catalogue
* `POST /api/v1/patch-panels/reconcile?apply=false` reconcile external records
* `GET /api/v1/patch-panels/export` CSV
