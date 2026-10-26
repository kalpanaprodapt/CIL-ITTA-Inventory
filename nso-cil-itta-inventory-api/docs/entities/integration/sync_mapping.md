# Sync Mapping

Domain: **Integration Layer** (`integration`) - ASGI app `itta_api.domains.integration.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `job_id` | `jobId` | Job Id | yes |
| `source_field` | `sourceField` | Source Field | yes |
| `target_field` | `targetField` | Target Field | yes |
| `transform` | `transform` | Transform | no |
| `required` | `required` | Required | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/sync-mappings` search
* `GET /api/v1/sync-mappings/meta` field catalogue
* `POST /api/v1/sync-mappings/reconcile?apply=false` reconcile external records
* `GET /api/v1/sync-mappings/export` CSV
