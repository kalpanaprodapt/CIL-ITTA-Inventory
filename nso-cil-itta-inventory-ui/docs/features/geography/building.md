# Building feature

Route prefix `/geography/buildings` - folder `src/features/geography/building`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `siteId` | Site Id | yes | `u` |
| `floors` | Floors | no | `i` |
| `yearBuilt` | Year Built | no | `i` |
| `ownership` | Ownership | no | `e` |
| `leaseExpiry` | Lease Expiry | no | `D` |
| `securityLevel` | Security Level | no | `i` |
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
* Site Id
* Floors
* Year Built
* Ownership
* Lease Expiry
* Security Level
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `BuildingListPage` - table, filters, bulk actions, CSV export
* `BuildingDetailPage` - read only view with status buttons
* `BuildingCreatePage` / `BuildingEditPage` - shared `BuildingForm`
