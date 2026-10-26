# Maintenance Window

Domain: **Change and Work Management** (`change`) - ASGI app `itta_api.domains.change.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `window_name` | `windowName` | Window Name | yes |
| `starts_at` | `startsAt` | Starts At | yes |
| `ends_at` | `endsAt` | Ends At | yes |
| `scope` | `scope` | Scope | no |
| `impact_level` | `impactLevel` | Impact Level | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/maintenance-windows` search
* `GET /api/v1/maintenance-windows/meta` field catalogue
* `POST /api/v1/maintenance-windows/reconcile?apply=false` reconcile external records
* `GET /api/v1/maintenance-windows/export` CSV
