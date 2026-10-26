# Contract feature

Route prefix `/customer/contracts` - folder `src/features/customer/contract`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `customerId` | Customer Id | yes | `u` |
| `contractNumber` | Contract Number | yes | `s` |
| `startDate` | Start Date | yes | `D` |
| `endDate` | End Date | no | `D` |
| `termMonths` | Term Months | no | `i` |
| `autoRenew` | Auto Renew | no | `b` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Customer Id
* Contract Number
* Start Date
* End Date
* Term Months
* Auto Renew
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `ContractListPage` - table, filters, bulk actions, CSV export
* `ContractDetailPage` - read only view with status buttons
* `ContractCreatePage` / `ContractEditPage` - shared `ContractForm`
