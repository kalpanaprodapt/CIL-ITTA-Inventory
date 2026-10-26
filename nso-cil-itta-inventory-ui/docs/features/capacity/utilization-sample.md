# Utilization Sample feature

Route prefix `/capacity/utilization-samples` - folder `src/features/capacity/utilization-sample`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `targetRef` | Target Ref | yes | `s` |
| `metric` | Metric | yes | `s` |
| `sampledAt` | Sampled At | yes | `T` |
| `valuePct` | Value Pct | yes | `d` |
| `peak` | Peak | no | `b` |
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
* Target Ref
* Metric
* Sampled At
* Value Pct
* Peak
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `UtilizationSampleListPage` - table, filters, bulk actions, CSV export
* `UtilizationSampleDetailPage` - read only view with status buttons
* `UtilizationSampleCreatePage` / `UtilizationSampleEditPage` - shared `UtilizationSampleForm`
