# Vpn Service feature

Route prefix `/mpls/vpn-services` - folder `src/features/mpls/vpn-service`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `vpnName` | Vpn Name | yes | `s` |
| `vpnType` | Vpn Type | no | `e` |
| `vrfId` | Vrf Id | no | `u` |
| `customerId` | Customer Id | no | `u` |
| `topology` | Topology | no | `e` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Vpn Name
* Vpn Type
* Vrf Id
* Customer Id
* Topology
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `VpnServiceListPage` - table, filters, bulk actions, CSV export
* `VpnServiceDetailPage` - read only view with status buttons
* `VpnServiceCreatePage` / `VpnServiceEditPage` - shared `VpnServiceForm`
