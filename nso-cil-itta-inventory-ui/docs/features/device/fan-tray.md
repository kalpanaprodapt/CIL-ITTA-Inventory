# Fan Tray feature

Route prefix `/device/fan-traies` - folder `src/features/device/fan-tray`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `chassisId` | Chassis Id | yes | `u` |
| `slotNumber` | Slot Number | no | `i` |
| `fanCount` | Fan Count | no | `i` |
| `airflow` | Airflow | no | `e` |
| `serialNumber` | Serial Number | no | `s` |
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
* Chassis Id
* Slot Number
* Fan Count
* Airflow
* Serial Number
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `FanTrayListPage` - table, filters, bulk actions, CSV export
* `FanTrayDetailPage` - read only view with status buttons
* `FanTrayCreatePage` / `FanTrayEditPage` - shared `FanTrayForm`
