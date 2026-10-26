# Warranty feature

Route prefix `/vendor/warranties` - folder `src/features/vendor/warranty`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `assetId` | Asset Id | yes | `u` |
| `startDate` | Start Date | yes | `D` |
| `endDate` | End Date | yes | `D` |
| `warrantyType` | Warranty Type | no | `e` |
| `terms` | Terms | no | `t` |
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
* Asset Id
* Start Date
* End Date
* Warranty Type
* Terms
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `WarrantyListPage` - table, filters, bulk actions, CSV export
* `WarrantyDetailPage` - read only view with status buttons
* `WarrantyCreatePage` / `WarrantyEditPage` - shared `WarrantyForm`
