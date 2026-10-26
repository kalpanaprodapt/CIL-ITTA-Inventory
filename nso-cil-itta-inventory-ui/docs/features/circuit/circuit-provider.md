# Circuit Provider feature

Route prefix `/circuit/circuit-providers` - folder `src/features/circuit/circuit-provider`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `providerName` | Provider Name | yes | `s` |
| `accountNumber` | Account Number | no | `s` |
| `noc` | Noc | no | `s` |
| `escalationEmail` | Escalation Email | no | `s` |
| `providerType` | Provider Type | no | `e` |
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
* Provider Name
* Account Number
* Noc
* Escalation Email
* Provider Type
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `CircuitProviderListPage` - table, filters, bulk actions, CSV export
* `CircuitProviderDetailPage` - read only view with status buttons
* `CircuitProviderCreatePage` / `CircuitProviderEditPage` - shared `CircuitProviderForm`
