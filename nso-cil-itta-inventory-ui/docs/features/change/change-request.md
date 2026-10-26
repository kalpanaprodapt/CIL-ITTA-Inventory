# Change Request feature

Route prefix `/change/change-requests` - folder `src/features/change/change-request`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `changeNumber` | Change Number | yes | `s` |
| `changeType` | Change Type | no | `e` |
| `riskLevel` | Risk Level | no | `e` |
| `plannedStart` | Planned Start | no | `T` |
| `plannedEnd` | Planned End | no | `T` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Change Number
* Change Type
* Risk Level
* Planned Start
* Planned End
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `ChangeRequestListPage` - table, filters, bulk actions, CSV export
* `ChangeRequestDetailPage` - read only view with status buttons
* `ChangeRequestCreatePage` / `ChangeRequestEditPage` - shared `ChangeRequestForm`
