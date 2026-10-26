# Power Feed feature

Route prefix `/facility/power-feeds` - folder `src/features/facility/power-feed`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `roomId` | Room Id | yes | `u` |
| `feedType` | Feed Type | no | `e` |
| `voltage` | Voltage | no | `d` |
| `amperage` | Amperage | no | `d` |
| `phase` | Phase | no | `s` |
| `breakerLabel` | Breaker Label | no | `s` |
| `redundancyGroup` | Redundancy Group | no | `s` |
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
* Room Id
* Feed Type
* Voltage
* Amperage
* Phase
* Breaker Label
* Redundancy Group
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `PowerFeedListPage` - table, filters, bulk actions, CSV export
* `PowerFeedDetailPage` - read only view with status buttons
* `PowerFeedCreatePage` / `PowerFeedEditPage` - shared `PowerFeedForm`
