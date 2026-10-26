# Depreciation Schedule feature

Route prefix `/assetmgmt/depreciation-schedules` - folder `src/features/assetmgmt/depreciation-schedule`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `assetId` | Asset Id | yes | `u` |
| `method` | Method | no | `e` |
| `usefulLifeMonths` | Useful Life Months | yes | `i` |
| `salvageValue` | Salvage Value | no | `d` |
| `startDate` | Start Date | no | `D` |
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
* Asset Id
* Method
* Useful Life Months
* Salvage Value
* Start Date
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `DepreciationScheduleListPage` - table, filters, bulk actions, CSV export
* `DepreciationScheduleDetailPage` - read only view with status buttons
* `DepreciationScheduleCreatePage` / `DepreciationScheduleEditPage` - shared `DepreciationScheduleForm`
