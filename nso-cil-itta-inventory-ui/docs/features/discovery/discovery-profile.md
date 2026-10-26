# Discovery Profile feature

Route prefix `/discovery/discovery-profiles` - folder `src/features/discovery/discovery-profile`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `profileName` | Profile Name | yes | `s` |
| `protocol` | Protocol | no | `e` |
| `credentialRef` | Credential Ref | no | `s` |
| `scopeCidr` | Scope Cidr | no | `s` |
| `scheduleCron` | Schedule Cron | no | `s` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Profile Name
* Protocol
* Credential Ref
* Scope Cidr
* Schedule Cron
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `DiscoveryProfileListPage` - table, filters, bulk actions, CSV export
* `DiscoveryProfileDetailPage` - read only view with status buttons
* `DiscoveryProfileCreatePage` / `DiscoveryProfileEditPage` - shared `DiscoveryProfileForm`
