# Maintenance Window feature

Route prefix `/change/maintenance-windows` - folder `src/features/change/maintenance-window`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `windowName` | Window Name | yes | `s` |
| `startsAt` | Starts At | yes | `T` |
| `endsAt` | Ends At | yes | `T` |
| `scope` | Scope | no | `s` |
| `impactLevel` | Impact Level | no | `e` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Window Name
* Starts At
* Ends At
* Scope
* Impact Level
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `MaintenanceWindowListPage` - table, filters, bulk actions, CSV export
* `MaintenanceWindowDetailPage` - read only view with status buttons
* `MaintenanceWindowCreatePage` / `MaintenanceWindowEditPage` - shared `MaintenanceWindowForm`
