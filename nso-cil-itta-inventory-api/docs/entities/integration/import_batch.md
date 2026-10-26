# Import Batch

Domain: **Integration Layer** (`integration`) - ASGI app `itta_api.domains.integration.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `file_name` | `fileName` | File Name | yes |
| `entity_type` | `entityType` | Entity Type | yes |
| `total_rows` | `totalRows` | Total Rows | no |
| `processed_rows` | `processedRows` | Processed Rows | no |
| `failed_rows` | `failedRows` | Failed Rows | no |
| `batch_state` | `batchState` | Batch State | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/import-batchs` search
* `GET /api/v1/import-batchs/meta` field catalogue
* `POST /api/v1/import-batchs/reconcile?apply=false` reconcile external records
* `GET /api/v1/import-batchs/export` CSV
