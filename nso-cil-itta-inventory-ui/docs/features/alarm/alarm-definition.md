# Alarm Definition feature

Route prefix `/alarm/alarm-definitions` - folder `src/features/alarm/alarm-definition`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `alarmCode` | Alarm Code | yes | `s` |
| `severity` | Severity | no | `e` |
| `probableCause` | Probable Cause | no | `s` |
| `autoClear` | Auto Clear | no | `b` |
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
* Alarm Code
* Severity
* Probable Cause
* Auto Clear
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `AlarmDefinitionListPage` - table, filters, bulk actions, CSV export
* `AlarmDefinitionDetailPage` - read only view with status buttons
* `AlarmDefinitionCreatePage` / `AlarmDefinitionEditPage` - shared `AlarmDefinitionForm`
