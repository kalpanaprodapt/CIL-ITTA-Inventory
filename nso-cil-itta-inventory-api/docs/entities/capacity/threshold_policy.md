# Threshold Policy

Domain: **Capacity Planning** (`capacity`) - ASGI app `itta_api.domains.capacity.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `policy_name` | `policyName` | Policy Name | yes |
| `metric` | `metric` | Metric | yes |
| `warn_pct` | `warnPct` | Warn Pct | yes |
| `crit_pct` | `critPct` | Crit Pct | yes |
| `evaluation_window_minutes` | `evaluationWindowMinutes` | Evaluation Window Minutes | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/threshold-policies` search
* `GET /api/v1/threshold-policies/meta` field catalogue
* `POST /api/v1/threshold-policies/reconcile?apply=false` reconcile external records
* `GET /api/v1/threshold-policies/export` CSV
