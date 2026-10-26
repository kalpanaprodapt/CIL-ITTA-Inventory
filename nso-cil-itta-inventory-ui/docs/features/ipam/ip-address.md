# Ip Address feature

Route prefix `/ipam/ip-addresses` - folder `src/features/ipam/ip-address`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `subnetId` | Subnet Id | yes | `u` |
| `address` | Address | yes | `s` |
| `assignedToId` | Assigned To Id | no | `u` |
| `assignmentType` | Assignment Type | no | `e` |
| `dnsName` | Dns Name | no | `s` |
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
* Subnet Id
* Address
* Assigned To Id
* Assignment Type
* Dns Name
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `IpAddressListPage` - table, filters, bulk actions, CSV export
* `IpAddressDetailPage` - read only view with status buttons
* `IpAddressCreatePage` / `IpAddressEditPage` - shared `IpAddressForm`
