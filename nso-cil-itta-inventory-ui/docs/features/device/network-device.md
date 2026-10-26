# Network Device feature

Route prefix `/device/network-devices` - folder `src/features/device/network-device`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `hostname` | Hostname | yes | `s` |
| `deviceRole` | Device Role | no | `e` |
| `mgmtIp` | Mgmt Ip | no | `s` |
| `serialNumber` | Serial Number | no | `s` |
| `modelId` | Model Id | no | `u` |
| `rackUnitId` | Rack Unit Id | no | `u` |
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
* Hostname
* Device Role
* Mgmt Ip
* Serial Number
* Model Id
* Rack Unit Id
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `NetworkDeviceListPage` - table, filters, bulk actions, CSV export
* `NetworkDeviceDetailPage` - read only view with status buttons
* `NetworkDeviceCreatePage` / `NetworkDeviceEditPage` - shared `NetworkDeviceForm`
