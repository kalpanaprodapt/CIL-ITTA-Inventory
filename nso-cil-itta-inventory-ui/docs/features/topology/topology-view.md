# Topology View feature

Route prefix `/topology/topology-views` - folder `src/features/topology/topology-view`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `viewName` | View Name | yes | `s` |
| `viewType` | View Type | no | `e` |
| `scopeFilter` | Scope Filter | no | `s` |
| `refreshedAt` | Refreshed At | no | `T` |
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
* View Name
* View Type
* Scope Filter
* Refreshed At
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `TopologyViewListPage` - table, filters, bulk actions, CSV export
* `TopologyViewDetailPage` - read only view with status buttons
* `TopologyViewCreatePage` / `TopologyViewEditPage` - shared `TopologyViewForm`
