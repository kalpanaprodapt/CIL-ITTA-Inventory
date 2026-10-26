# Vendor feature

Route prefix `/vendor/vendors` - folder `src/features/vendor/vendor`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `legalName` | Legal Name | yes | `s` |
| `supportEmail` | Support Email | no | `s` |
| `supportPhone` | Support Phone | no | `s` |
| `website` | Website | no | `s` |
| `tier` | Tier | no | `e` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Legal Name
* Support Email
* Support Phone
* Website
* Tier
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `VendorListPage` - table, filters, bulk actions, CSV export
* `VendorDetailPage` - read only view with status buttons
* `VendorCreatePage` / `VendorEditPage` - shared `VendorForm`
