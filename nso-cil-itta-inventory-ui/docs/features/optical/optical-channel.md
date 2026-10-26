# Optical Channel feature

Route prefix `/optical/optical-channels` - folder `src/features/optical/optical-channel`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `wavelengthNm` | Wavelength Nm | yes | `d` |
| `channelNumber` | Channel Number | yes | `i` |
| `gridType` | Grid Type | no | `e` |
| `bitrateGbps` | Bitrate Gbps | no | `d` |
| `systemId` | System Id | no | `u` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Wavelength Nm
* Channel Number
* Grid Type
* Bitrate Gbps
* System Id
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `OpticalChannelListPage` - table, filters, bulk actions, CSV export
* `OpticalChannelDetailPage` - read only view with status buttons
* `OpticalChannelCreatePage` / `OpticalChannelEditPage` - shared `OpticalChannelForm`
