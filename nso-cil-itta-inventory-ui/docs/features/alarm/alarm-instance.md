# Alarm Instance feature

Route prefix `/alarm/alarm-instances` - folder `src/features/alarm/alarm-instance`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `definitionId` | Definition Id | yes | `u` |
| `sourceRef` | Source Ref | yes | `s` |
| `raisedAt` | Raised At | yes | `T` |
| `clearedAt` | Cleared At | no | `T` |
| `ackedBy` | Acked By | no | `s` |
| `alarmState` | Alarm State | no | `e` |
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
* Definition Id
* Source Ref
* Raised At
* Cleared At
* Acked By
* Alarm State
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `AlarmInstanceListPage` - table, filters, bulk actions, CSV export
* `AlarmInstanceDetailPage` - read only view with status buttons
* `AlarmInstanceCreatePage` / `AlarmInstanceEditPage` - shared `AlarmInstanceForm`
