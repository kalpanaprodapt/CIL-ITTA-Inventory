# Compliance Exception

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
| `justification` | `justification` | Justification | yes |
| `approved_by` | `approvedBy` | Approved By | no |
| `expires_on` | `expiresOn` | Expires On | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/compliance-exceptions` search
* `GET /api/v1/compliance-exceptions/meta` field catalogue
* `POST /api/v1/compliance-exceptions/reconcile?apply=false` reconcile external records
* `GET /api/v1/compliance-exceptions/export` CSV
