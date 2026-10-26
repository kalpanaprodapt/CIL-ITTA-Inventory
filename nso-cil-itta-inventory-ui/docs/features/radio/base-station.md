# Base Station feature

Route prefix `/radio/base-stations` - folder `src/features/radio/base-station`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `siteId` | Site Id | yes | `u` |
| `enbId` | Enb Id | yes | `l` |
| `technology` | Technology | no | `e` |
| `sectorCount` | Sector Count | no | `i` |
| `cellCount` | Cell Count | no | `i` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Site Id
* Enb Id
* Technology
* Sector Count
* Cell Count
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `BaseStationListPage` - table, filters, bulk actions, CSV export
* `BaseStationDetailPage` - read only view with status buttons
* `BaseStationCreatePage` / `BaseStationEditPage` - shared `BaseStationForm`
