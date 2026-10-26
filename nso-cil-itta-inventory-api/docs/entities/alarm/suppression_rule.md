# Suppression Rule

Domain: **Alarms and Events** (`alarm`) - ASGI app `itta_api.domains.alarm.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `rule_name` | `ruleName` | Rule Name | yes |
| `match_expression` | `matchExpression` | Match Expression | yes |
| `starts_at` | `startsAt` | Starts At | no |
| `ends_at` | `endsAt` | Ends At | no |
| `reason` | `reason` | Reason | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/suppression-rules` search
* `GET /api/v1/suppression-rules/meta` field catalogue
* `POST /api/v1/suppression-rules/reconcile?apply=false` reconcile external records
* `GET /api/v1/suppression-rules/export` CSV
