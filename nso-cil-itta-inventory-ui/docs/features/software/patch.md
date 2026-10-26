# Patch feature

Route prefix `/software/patchs` - folder `src/features/software/patch`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `versionId` | Version Id | yes | `u` |
| `patchId` | Patch Id | yes | `s` |
| `severity` | Severity | no | `e` |
| `releasedOn` | Released On | no | `D` |
| `rebootRequired` | Reboot Required | no | `b` |
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
* Version Id
* Patch Id
* Severity
* Released On
* Reboot Required
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `PatchListPage` - table, filters, bulk actions, CSV export
* `PatchDetailPage` - read only view with status buttons
* `PatchCreatePage` / `PatchEditPage` - shared `PatchForm`
