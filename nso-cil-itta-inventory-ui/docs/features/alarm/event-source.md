# Event Source feature

Route prefix `/alarm/event-sources` - folder `src/features/alarm/event-source`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `sourceName` | Source Name | yes | `s` |
| `protocol` | Protocol | no | `e` |
| `endpoint` | Endpoint | no | `s` |
| `heartbeatSeconds` | Heartbeat Seconds | no | `i` |
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
* Source Name
* Protocol
* Endpoint
* Heartbeat Seconds
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `EventSourceListPage` - table, filters, bulk actions, CSV export
* `EventSourceDetailPage` - read only view with status buttons
* `EventSourceCreatePage` / `EventSourceEditPage` - shared `EventSourceForm`
