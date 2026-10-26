# Sync Mapping feature

Route prefix `/integration/sync-mappings` - folder `src/features/integration/sync-mapping`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `jobId` | Job Id | yes | `u` |
| `sourceField` | Source Field | yes | `s` |
| `targetField` | Target Field | yes | `s` |
| `transform` | Transform | no | `s` |
| `required` | Required | no | `b` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Job Id
* Source Field
* Target Field
* Transform
* Required
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `SyncMappingListPage` - table, filters, bulk actions, CSV export
* `SyncMappingDetailPage` - read only view with status buttons
* `SyncMappingCreatePage` / `SyncMappingEditPage` - shared `SyncMappingForm`
