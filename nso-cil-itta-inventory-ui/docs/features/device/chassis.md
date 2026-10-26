# Chassis feature

Route prefix `/device/chassises` - folder `src/features/device/chassis`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `deviceId` | Device Id | yes | `u` |
| `slotCount` | Slot Count | yes | `i` |
| `serialNumber` | Serial Number | no | `s` |
| `backplaneGbps` | Backplane Gbps | no | `d` |
| `redundancyMode` | Redundancy Mode | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Device Id
* Slot Count
* Serial Number
* Backplane Gbps
* Redundancy Mode
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `ChassisListPage` - table, filters, bulk actions, CSV export
* `ChassisDetailPage` - read only view with status buttons
* `ChassisCreatePage` / `ChassisEditPage` - shared `ChassisForm`
