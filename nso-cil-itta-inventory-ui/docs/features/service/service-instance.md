# Service Instance feature

Route prefix `/service/service-instances` - folder `src/features/service/service-instance`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `catalogItemId` | Catalog Item Id | yes | `u` |
| `customerId` | Customer Id | yes | `u` |
| `activatedOn` | Activated On | no | `D` |
| `bandwidthMbps` | Bandwidth Mbps | no | `l` |
| `serviceState` | Service State | no | `e` |
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
* Catalog Item Id
* Customer Id
* Activated On
* Bandwidth Mbps
* Service State
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `ServiceInstanceListPage` - table, filters, bulk actions, CSV export
* `ServiceInstanceDetailPage` - read only view with status buttons
* `ServiceInstanceCreatePage` / `ServiceInstanceEditPage` - shared `ServiceInstanceForm`
