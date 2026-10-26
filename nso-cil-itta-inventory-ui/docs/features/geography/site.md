# Site feature

Route prefix `/geography/sites` - folder `src/features/geography/site`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `cityId` | City Id | yes | `u` |
| `siteType` | Site Type | no | `e` |
| `address` | Address | no | `s` |
| `latitude` | Latitude | no | `d` |
| `longitude` | Longitude | no | `d` |
| `powerKw` | Power Kw | no | `d` |
| `accessHours` | Access Hours | no | `s` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* City Id
* Site Type
* Address
* Latitude
* Longitude
* Power Kw
* Access Hours
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `SiteListPage` - table, filters, bulk actions, CSV export
* `SiteDetailPage` - read only view with status buttons
* `SiteCreatePage` / `SiteEditPage` - shared `SiteForm`
