# Capacity Plan feature

Route prefix `/capacity/capacity-plans` - folder `src/features/capacity/capacity-plan`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `planName` | Plan Name | yes | `s` |
| `horizonMonths` | Horizon Months | yes | `i` |
| `targetUtilizationPct` | Target Utilization Pct | no | `d` |
| `owner` | Owner | no | `s` |
| `approvedOn` | Approved On | no | `D` |
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
* Plan Name
* Horizon Months
* Target Utilization Pct
* Owner
* Approved On
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `CapacityPlanListPage` - table, filters, bulk actions, CSV export
* `CapacityPlanDetailPage` - read only view with status buttons
* `CapacityPlanCreatePage` / `CapacityPlanEditPage` - shared `CapacityPlanForm`
