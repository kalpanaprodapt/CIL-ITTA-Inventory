# Cloud Account feature

Route prefix `/virtual/cloud-accounts` - folder `src/features/virtual/cloud-account`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `provider` | Provider | no | `e` |
| `accountRef` | Account Ref | yes | `s` |
| `subscriptionName` | Subscription Name | no | `s` |
| `monthlyBudget` | Monthly Budget | no | `d` |
| `environment` | Environment | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Provider
* Account Ref
* Subscription Name
* Monthly Budget
* Environment
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `CloudAccountListPage` - table, filters, bulk actions, CSV export
* `CloudAccountDetailPage` - read only view with status buttons
* `CloudAccountCreatePage` / `CloudAccountEditPage` - shared `CloudAccountForm`
