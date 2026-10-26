# Ip Pool feature

Route prefix `/ipam/ip-pools` - folder `src/features/ipam/ip-pool`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `subnetId` | Subnet Id | yes | `u` |
| `rangeStart` | Range Start | yes | `s` |
| `rangeEnd` | Range End | yes | `s` |
| `poolType` | Pool Type | no | `e` |
| `leaseSeconds` | Lease Seconds | no | `i` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Subnet Id
* Range Start
* Range End
* Pool Type
* Lease Seconds
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `IpPoolListPage` - table, filters, bulk actions, CSV export
* `IpPoolDetailPage` - read only view with status buttons
* `IpPoolCreatePage` / `IpPoolEditPage` - shared `IpPoolForm`
