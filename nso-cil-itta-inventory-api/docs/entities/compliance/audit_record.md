# Audit Record

Domain: **Compliance and Audit** (`compliance`) - ASGI app `itta_api.domains.compliance.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `actor` | `actor` | Actor | yes |
| `action` | `action` | Action | yes |
| `entity_ref` | `entityRef` | Entity Ref | yes |
| `occurred_at` | `occurredAt` | Occurred At | yes |
| `before_state` | `beforeState` | Before State | no |
| `after_state` | `afterState` | After State | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/audit-records` search
* `GET /api/v1/audit-records/meta` field catalogue
* `POST /api/v1/audit-records/reconcile?apply=false` reconcile external records
* `GET /api/v1/audit-records/export` CSV
