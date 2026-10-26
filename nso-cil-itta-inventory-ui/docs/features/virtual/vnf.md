# Vnf feature

Route prefix `/virtual/vnfs` - folder `src/features/virtual/vnf`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `vnfName` | Vnf Name | yes | `s` |
| `vnfType` | Vnf Type | yes | `s` |
| `flavor` | Flavor | no | `s` |
| `vcpu` | Vcpu | no | `i` |
| `memoryGb` | Memory Gb | no | `i` |
| `instanceCount` | Instance Count | no | `i` |
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
* Vnf Name
* Vnf Type
* Flavor
* Vcpu
* Memory Gb
* Instance Count
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `VnfListPage` - table, filters, bulk actions, CSV export
* `VnfDetailPage` - read only view with status buttons
* `VnfCreatePage` / `VnfEditPage` - shared `VnfForm`
