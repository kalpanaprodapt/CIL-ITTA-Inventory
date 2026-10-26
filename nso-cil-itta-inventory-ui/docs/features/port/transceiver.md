# Transceiver feature

Route prefix `/port/transceivers` - folder `src/features/port/transceiver`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `portId` | Port Id | no | `u` |
| `formFactor` | Form Factor | no | `e` |
| `wavelengthNm` | Wavelength Nm | no | `i` |
| `reachKm` | Reach Km | no | `d` |
| `serialNumber` | Serial Number | no | `s` |
| `vendorPart` | Vendor Part | no | `s` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Port Id
* Form Factor
* Wavelength Nm
* Reach Km
* Serial Number
* Vendor Part
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `TransceiverListPage` - table, filters, bulk actions, CSV export
* `TransceiverDetailPage` - read only view with status buttons
* `TransceiverCreatePage` / `TransceiverEditPage` - shared `TransceiverForm`
