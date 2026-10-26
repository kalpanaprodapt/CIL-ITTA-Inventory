# Reconciliation Rule

Domain: **Discovery and Reconciliation** (`discovery`) - ASGI app `itta_api.domains.discovery.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `rule_name` | `ruleName` | Rule Name | yes |
| `match_key` | `matchKey` | Match Key | yes |
| `action` | `action` | Action | no |
| `priority` | `priority` | Priority | yes |
| `expression` | `expression` | Expression | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/reconciliation-rules` search
* `GET /api/v1/reconciliation-rules/meta` field catalogue
* `POST /api/v1/reconciliation-rules/reconcile?apply=false` reconcile external records
* `GET /api/v1/reconciliation-rules/export` CSV
