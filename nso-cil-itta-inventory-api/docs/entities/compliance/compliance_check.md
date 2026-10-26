# Compliance Check

Domain: **Compliance and Audit** (`compliance`) - ASGI app `itta_api.domains.compliance.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `rule_id` | `ruleId` | Rule Id | yes |
| `target_ref` | `targetRef` | Target Ref | yes |
| `checked_at` | `checkedAt` | Checked At | yes |
| `result` | `result` | Result | no |
| `evidence` | `evidence` | Evidence | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/compliance-checks` search
* `GET /api/v1/compliance-checks/meta` field catalogue
* `POST /api/v1/compliance-checks/reconcile?apply=false` reconcile external records
* `GET /api/v1/compliance-checks/export` CSV
