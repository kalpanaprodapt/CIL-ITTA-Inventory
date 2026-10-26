# Reconciliation Result

Domain: **Discovery and Reconciliation** (`discovery`) - ASGI app `itta_api.domains.discovery.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `job_id` | `jobId` | Job Id | yes |
| `rule_id` | `ruleId` | Rule Id | no |
| `outcome` | `outcome` | Outcome | no |
| `diff_summary` | `diffSummary` | Diff Summary | no |
| `resolved_by` | `resolvedBy` | Resolved By | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/reconciliation-results` search
* `GET /api/v1/reconciliation-results/meta` field catalogue
* `POST /api/v1/reconciliation-results/reconcile?apply=false` reconcile external records
* `GET /api/v1/reconciliation-results/export` CSV
