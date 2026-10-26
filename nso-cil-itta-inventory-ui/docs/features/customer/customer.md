# Customer feature

Route prefix `/customer/customers` - folder `src/features/customer/customer`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `legalName` | Legal Name | yes | `s` |
| `segment` | Segment | no | `e` |
| `taxId` | Tax Id | no | `s` |
| `industry` | Industry | no | `s` |
| `accountManager` | Account Manager | no | `s` |
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
* Legal Name
* Segment
* Tax Id
* Industry
* Account Manager
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `CustomerListPage` - table, filters, bulk actions, CSV export
* `CustomerDetailPage` - read only view with status buttons
* `CustomerCreatePage` / `CustomerEditPage` - shared `CustomerForm`
