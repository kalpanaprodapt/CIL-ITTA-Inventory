# Circuit feature

Route prefix `/circuit/circuits` - folder `src/features/circuit/circuit`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `circuitId` | Circuit Id | yes | `s` |
| `circuitType` | Circuit Type | no | `e` |
| `bandwidthMbps` | Bandwidth Mbps | yes | `l` |
| `providerId` | Provider Id | no | `u` |
| `aSiteId` | A Site Id | no | `u` |
| `zSiteId` | Z Site Id | no | `u` |
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
* Circuit Id
* Circuit Type
* Bandwidth Mbps
* Provider Id
* A Site Id
* Z Site Id
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `CircuitListPage` - table, filters, bulk actions, CSV export
* `CircuitDetailPage` - read only view with status buttons
* `CircuitCreatePage` / `CircuitEditPage` - shared `CircuitForm`
