# Backhaul Link feature

Route prefix `/radio/backhaul-links` - folder `src/features/radio/backhaul-link`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `baseStationId` | Base Station Id | yes | `u` |
| `linkMedium` | Link Medium | no | `e` |
| `capacityMbps` | Capacity Mbps | yes | `l` |
| `latencyMs` | Latency Ms | no | `d` |
| `circuitRef` | Circuit Ref | no | `s` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Base Station Id
* Link Medium
* Capacity Mbps
* Latency Ms
* Circuit Ref
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `BackhaulLinkListPage` - table, filters, bulk actions, CSV export
* `BackhaulLinkDetailPage` - read only view with status buttons
* `BackhaulLinkCreatePage` / `BackhaulLinkEditPage` - shared `BackhaulLinkForm`
