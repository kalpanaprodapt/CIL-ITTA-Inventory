# Radio Unit feature

Route prefix `/radio/radio-units` - folder `src/features/radio/radio-unit`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `siteId` | Site Id | yes | `u` |
| `unitType` | Unit Type | no | `e` |
| `bandMhz` | Band Mhz | no | `i` |
| `txPowerDbm` | Tx Power Dbm | no | `d` |
| `serialNumber` | Serial Number | no | `s` |
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
* Site Id
* Unit Type
* Band Mhz
* Tx Power Dbm
* Serial Number
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `RadioUnitListPage` - table, filters, bulk actions, CSV export
* `RadioUnitDetailPage` - read only view with status buttons
* `RadioUnitCreatePage` / `RadioUnitEditPage` - shared `RadioUnitForm`
