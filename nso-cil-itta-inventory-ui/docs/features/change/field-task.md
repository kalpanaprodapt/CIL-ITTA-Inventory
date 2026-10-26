# Field Task feature

Route prefix `/change/field-tasks` - folder `src/features/change/field-task`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `workOrderId` | Work Order Id | yes | `u` |
| `taskName` | Task Name | yes | `s` |
| `sequence` | Sequence | yes | `i` |
| `durationMinutes` | Duration Minutes | no | `i` |
| `completedOn` | Completed On | no | `T` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Work Order Id
* Task Name
* Sequence
* Duration Minutes
* Completed On
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `FieldTaskListPage` - table, filters, bulk actions, CSV export
* `FieldTaskDetailPage` - read only view with status buttons
* `FieldTaskCreatePage` / `FieldTaskEditPage` - shared `FieldTaskForm`
