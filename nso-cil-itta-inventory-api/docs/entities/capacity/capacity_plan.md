# Capacity Plan

Domain: **Capacity Planning** (`capacity`) - ASGI app `itta_api.domains.capacity.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `plan_name` | `planName` | Plan Name | yes |
| `horizon_months` | `horizonMonths` | Horizon Months | yes |
| `target_utilization_pct` | `targetUtilizationPct` | Target Utilization Pct | no |
| `owner` | `owner` | Owner | no |
| `approved_on` | `approvedOn` | Approved On | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/capacity-plans` search
* `GET /api/v1/capacity-plans/meta` field catalogue
* `POST /api/v1/capacity-plans/reconcile?apply=false` reconcile external records
* `GET /api/v1/capacity-plans/export` CSV
