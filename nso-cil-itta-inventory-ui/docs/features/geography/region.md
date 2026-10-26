# Region feature

Route prefix `/geography/regions` - folder `src/features/geography/region`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `countryId` | Country Id | yes | `u` |
| `regionType` | Region Type | no | `e` |
| `population` | Population | no | `l` |
| `latitude` | Latitude | no | `d` |
| `longitude` | Longitude | no | `d` |
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
* Country Id
* Region Type
* Population
* Latitude
* Longitude
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `RegionListPage` - table, filters, bulk actions, CSV export
* `RegionDetailPage` - read only view with status buttons
* `RegionCreatePage` / `RegionEditPage` - shared `RegionForm`
