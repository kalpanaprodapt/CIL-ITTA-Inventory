# Sector feature

Route prefix `/radio/sectors` - folder `src/features/radio/sector`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `baseStationId` | Base Station Id | yes | `u` |
| `sectorNumber` | Sector Number | yes | `i` |
| `pci` | Pci | no | `i` |
| `frequencyMhz` | Frequency Mhz | no | `i` |
| `bandwidthMhz` | Bandwidth Mhz | no | `i` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Base Station Id
* Sector Number
* Pci
* Frequency Mhz
* Bandwidth Mhz
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `SectorListPage` - table, filters, bulk actions, CSV export
* `SectorDetailPage` - read only view with status buttons
* `SectorCreatePage` / `SectorEditPage` - shared `SectorForm`
