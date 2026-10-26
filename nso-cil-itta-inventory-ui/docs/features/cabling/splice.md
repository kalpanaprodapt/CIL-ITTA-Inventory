# Splice feature

Route prefix `/cabling/splices` - folder `src/features/cabling/splice`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `enclosureId` | Enclosure Id | yes | `s` |
| `aStrandId` | A Strand Id | no | `u` |
| `zStrandId` | Z Strand Id | no | `u` |
| `lossDb` | Loss Db | no | `d` |
| `splicedOn` | Spliced On | no | `D` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Enclosure Id
* A Strand Id
* Z Strand Id
* Loss Db
* Spliced On
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `SpliceListPage` - table, filters, bulk actions, CSV export
* `SpliceDetailPage` - read only view with status buttons
* `SpliceCreatePage` / `SpliceEditPage` - shared `SpliceForm`
