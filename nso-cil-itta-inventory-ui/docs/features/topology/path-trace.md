# Path Trace feature

Route prefix `/topology/path-traces` - folder `src/features/topology/path-trace`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `sourceRef` | Source Ref | yes | `s` |
| `targetRef` | Target Ref | yes | `s` |
| `hopCount` | Hop Count | no | `i` |
| `totalLatencyMs` | Total Latency Ms | no | `d` |
| `traceMethod` | Trace Method | no | `s` |
| `tracedAt` | Traced At | no | `T` |
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
* Source Ref
* Target Ref
* Hop Count
* Total Latency Ms
* Trace Method
* Traced At
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `PathTraceListPage` - table, filters, bulk actions, CSV export
* `PathTraceDetailPage` - read only view with status buttons
* `PathTraceCreatePage` / `PathTraceEditPage` - shared `PathTraceForm`
