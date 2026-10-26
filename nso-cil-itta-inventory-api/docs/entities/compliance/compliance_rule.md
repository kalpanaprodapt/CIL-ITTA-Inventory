# Compliance Rule

Domain: **Compliance and Audit** (`compliance`) - ASGI app `itta_api.domains.compliance.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `rule_code` | `ruleCode` | Rule Code | yes |
| `framework` | `framework` | Framework | no |
| `severity` | `severity` | Severity | no |
| `expression` | `expression` | Expression | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/compliance-rules` search
* `GET /api/v1/compliance-rules/meta` field catalogue
* `POST /api/v1/compliance-rules/reconcile?apply=false` reconcile external records
* `GET /api/v1/compliance-rules/export` CSV
