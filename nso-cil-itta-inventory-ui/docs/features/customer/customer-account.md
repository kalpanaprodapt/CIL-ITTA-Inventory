# Customer Account feature

Route prefix `/customer/customer-accounts` - folder `src/features/customer/customer-account`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `customerId` | Customer Id | yes | `u` |
| `accountNumber` | Account Number | yes | `s` |
| `billingCycle` | Billing Cycle | no | `e` |
| `currency` | Currency | no | `s` |
| `creditLimit` | Credit Limit | no | `d` |
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
* Customer Id
* Account Number
* Billing Cycle
* Currency
* Credit Limit
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `CustomerAccountListPage` - table, filters, bulk actions, CSV export
* `CustomerAccountDetailPage` - read only view with status buttons
* `CustomerAccountCreatePage` / `CustomerAccountEditPage` - shared `CustomerAccountForm`
