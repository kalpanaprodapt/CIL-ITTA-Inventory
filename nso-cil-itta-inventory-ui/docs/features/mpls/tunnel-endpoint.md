# Tunnel Endpoint feature

Route prefix `/mpls/tunnel-endpoints` - folder `src/features/mpls/tunnel-endpoint`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `deviceId` | Device Id | yes | `u` |
| `tunnelName` | Tunnel Name | yes | `s` |
| `tunnelType` | Tunnel Type | no | `e` |
| `sourceIp` | Source Ip | no | `s` |
| `destinationIp` | Destination Ip | no | `s` |
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
* Device Id
* Tunnel Name
* Tunnel Type
* Source Ip
* Destination Ip
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `TunnelEndpointListPage` - table, filters, bulk actions, CSV export
* `TunnelEndpointDetailPage` - read only view with status buttons
* `TunnelEndpointCreatePage` / `TunnelEndpointEditPage` - shared `TunnelEndpointForm`
