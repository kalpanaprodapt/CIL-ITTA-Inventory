# Support Contract feature

Route prefix `/vendor/support-contracts` - folder `src/features/vendor/support-contract`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `vendorId` | Vendor Id | yes | `u` |
| `contractNumber` | Contract Number | yes | `s` |
| `startDate` | Start Date | yes | `D` |
| `endDate` | End Date | yes | `D` |
| `coverageLevel` | Coverage Level | no | `e` |
| `annualCost` | Annual Cost | no | `d` |
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
* Vendor Id
* Contract Number
* Start Date
* End Date
* Coverage Level
* Annual Cost
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `SupportContractListPage` - table, filters, bulk actions, CSV export
* `SupportContractDetailPage` - read only view with status buttons
* `SupportContractCreatePage` / `SupportContractEditPage` - shared `SupportContractForm`
