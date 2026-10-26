# License Pool feature

Route prefix `/software/license-pools` - folder `src/features/software/license-pool`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `vendorId` | Vendor Id | yes | `u` |
| `poolName` | Pool Name | yes | `s` |
| `totalSeats` | Total Seats | yes | `i` |
| `usedSeats` | Used Seats | no | `i` |
| `renewalDate` | Renewal Date | no | `D` |
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
* Vendor Id
* Pool Name
* Total Seats
* Used Seats
* Renewal Date
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `LicensePoolListPage` - table, filters, bulk actions, CSV export
* `LicensePoolDetailPage` - read only view with status buttons
* `LicensePoolCreatePage` / `LicensePoolEditPage` - shared `LicensePoolForm`
