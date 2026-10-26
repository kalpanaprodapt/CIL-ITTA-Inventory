# Customer Contact feature

Route prefix `/customer/customer-contacts` - folder `src/features/customer/customer-contact`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `customerId` | Customer Id | yes | `u` |
| `fullName` | Full Name | yes | `s` |
| `email` | Email | no | `s` |
| `phone` | Phone | no | `s` |
| `contactRole` | Contact Role | no | `e` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Customer Id
* Full Name
* Email
* Phone
* Contact Role
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `CustomerContactListPage` - table, filters, bulk actions, CSV export
* `CustomerContactDetailPage` - read only view with status buttons
* `CustomerContactCreatePage` / `CustomerContactEditPage` - shared `CustomerContactForm`
