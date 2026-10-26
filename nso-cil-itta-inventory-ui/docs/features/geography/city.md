# City feature

Route prefix `/geography/cities` - folder `src/features/geography/city`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `regionId` | Region Id | yes | `u` |
| `postalPrefix` | Postal Prefix | no | `s` |
| `population` | Population | no | `l` |
| `latitude` | Latitude | no | `d` |
| `longitude` | Longitude | no | `d` |
| `metroArea` | Metro Area | no | `s` |
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
* Region Id
* Postal Prefix
* Population
* Latitude
* Longitude
* Metro Area
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `CityListPage` - table, filters, bulk actions, CSV export
* `CityDetailPage` - read only view with status buttons
* `CityCreatePage` / `CityEditPage` - shared `CityForm`
