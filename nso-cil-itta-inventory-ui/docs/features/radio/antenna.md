# Antenna feature

Route prefix `/radio/antennas` - folder `src/features/radio/antenna`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `sectorId` | Sector Id | yes | `u` |
| `model` | Model | no | `s` |
| `heightM` | Height M | no | `d` |
| `azimuthDeg` | Azimuth Deg | no | `d` |
| `tiltDeg` | Tilt Deg | no | `d` |
| `gainDbi` | Gain Dbi | no | `d` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Sector Id
* Model
* Height M
* Azimuth Deg
* Tilt Deg
* Gain Dbi
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `AntennaListPage` - table, filters, bulk actions, CSV export
* `AntennaDetailPage` - read only view with status buttons
* `AntennaCreatePage` / `AntennaEditPage` - shared `AntennaForm`
