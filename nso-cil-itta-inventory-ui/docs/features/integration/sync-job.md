# Sync Job feature

Route prefix `/integration/sync-jobs` - folder `src/features/integration/sync-job`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `endpointId` | Endpoint Id | yes | `u` |
| `direction` | Direction | no | `e` |
| `scheduleCron` | Schedule Cron | no | `s` |
| `lastRunAt` | Last Run At | no | `T` |
| `lastStatus` | Last Status | no | `e` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Endpoint Id
* Direction
* Schedule Cron
* Last Run At
* Last Status
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `SyncJobListPage` - table, filters, bulk actions, CSV export
* `SyncJobDetailPage` - read only view with status buttons
* `SyncJobCreatePage` / `SyncJobEditPage` - shared `SyncJobForm`
