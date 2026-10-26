# Sub Interface feature

Route prefix `/port/sub-interfaces` - folder `src/features/port/sub-interface`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `interfaceId` | Interface Id | yes | `u` |
| `vlanTag` | Vlan Tag | yes | `i` |
| `encapsulation` | Encapsulation | no | `s` |
| `ipv4Address` | Ipv4 Address | no | `s` |
| `ipv6Address` | Ipv6 Address | no | `s` |
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
* Interface Id
* Vlan Tag
* Encapsulation
* Ipv4 Address
* Ipv6 Address
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `SubInterfaceListPage` - table, filters, bulk actions, CSV export
* `SubInterfaceDetailPage` - read only view with status buttons
* `SubInterfaceCreatePage` / `SubInterfaceEditPage` - shared `SubInterfaceForm`
