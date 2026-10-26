# License Key feature

Route prefix `/software/license-keies` - folder `src/features/software/license-key`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `poolId` | Pool Id | yes | `u` |
| `keyValue` | Key Value | yes | `s` |
| `feature` | Feature | yes | `s` |
| `expiresOn` | Expires On | no | `D` |
| `assignedDeviceId` | Assigned Device Id | no | `u` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Pool Id
* Key Value
* Feature
* Expires On
* Assigned Device Id
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `LicenseKeyListPage` - table, filters, bulk actions, CSV export
* `LicenseKeyDetailPage` - read only view with status buttons
* `LicenseKeyCreatePage` / `LicenseKeyEditPage` - shared `LicenseKeyForm`
