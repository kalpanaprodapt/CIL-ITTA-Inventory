# Sla Profile feature

Route prefix `/customer/sla-profiles` - folder `src/features/customer/sla-profile`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `profileName` | Profile Name | yes | `s` |
| `availabilityPct` | Availability Pct | yes | `d` |
| `mttrMinutes` | Mttr Minutes | no | `i` |
| `latencyMs` | Latency Ms | no | `d` |
| `jitterMs` | Jitter Ms | no | `d` |
| `creditPolicy` | Credit Policy | no | `t` |
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
* Profile Name
* Availability Pct
* Mttr Minutes
* Latency Ms
* Jitter Ms
* Credit Policy
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `SlaProfileListPage` - table, filters, bulk actions, CSV export
* `SlaProfileDetailPage` - read only view with status buttons
* `SlaProfileCreatePage` / `SlaProfileEditPage` - shared `SlaProfileForm`
