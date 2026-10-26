# Utilization Sample

Domain: **Capacity Planning** (`capacity`) - ASGI app `itta_api.domains.capacity.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `target_ref` | `targetRef` | Target Ref | yes |
| `metric` | `metric` | Metric | yes |
| `sampled_at` | `sampledAt` | Sampled At | yes |
| `value_pct` | `valuePct` | Value Pct | yes |
| `peak` | `peak` | Peak | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/utilization-samples` search
* `GET /api/v1/utilization-samples/meta` field catalogue
* `POST /api/v1/utilization-samples/reconcile?apply=false` reconcile external records
* `GET /api/v1/utilization-samples/export` CSV
