# Import Batch feature

Route prefix `/integration/import-batchs` - folder `src/features/integration/import-batch`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `fileName` | File Name | yes | `s` |
| `entityType` | Entity Type | yes | `s` |
| `totalRows` | Total Rows | no | `i` |
| `processedRows` | Processed Rows | no | `i` |
| `failedRows` | Failed Rows | no | `i` |
| `batchState` | Batch State | no | `e` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* File Name
* Entity Type
* Total Rows
* Processed Rows
* Failed Rows
* Batch State
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `ImportBatchListPage` - table, filters, bulk actions, CSV export
* `ImportBatchDetailPage` - read only view with status buttons
* `ImportBatchCreatePage` / `ImportBatchEditPage` - shared `ImportBatchForm`
