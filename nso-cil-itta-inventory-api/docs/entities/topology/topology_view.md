# Topology View

Domain: **Topology and Links** (`topology`) - ASGI app `itta_api.domains.topology.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `view_name` | `viewName` | View Name | yes |
| `view_type` | `viewType` | View Type | no |
| `scope_filter` | `scopeFilter` | Scope Filter | no |
| `refreshed_at` | `refreshedAt` | Refreshed At | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/topology-views` search
* `GET /api/v1/topology-views/meta` field catalogue
* `POST /api/v1/topology-views/reconcile?apply=false` reconcile external records
* `GET /api/v1/topology-views/export` CSV
