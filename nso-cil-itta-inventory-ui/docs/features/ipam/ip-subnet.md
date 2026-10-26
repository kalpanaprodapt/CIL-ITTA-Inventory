# Ip Subnet feature

Route prefix `/ipam/ip-subnets` - folder `src/features/ipam/ip-subnet`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `cidr` | Cidr | yes | `s` |
| `ipVersion` | Ip Version | no | `e` |
| `vrfId` | Vrf Id | no | `u` |
| `gateway` | Gateway | no | `s` |
| `vlanId` | Vlan Id | no | `u` |
| `utilizationPct` | Utilization Pct | no | `d` |
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
* Cidr
* Ip Version
* Vrf Id
* Gateway
* Vlan Id
* Utilization Pct
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `IpSubnetListPage` - table, filters, bulk actions, CSV export
* `IpSubnetDetailPage` - read only view with status buttons
* `IpSubnetCreatePage` / `IpSubnetEditPage` - shared `IpSubnetForm`
