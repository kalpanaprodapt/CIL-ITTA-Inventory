# Circuit Termination feature

Route prefix `/circuit/circuit-terminations` - folder `src/features/circuit/circuit-termination`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `circuitId` | Circuit Id | yes | `u` |
| `side` | Side | no | `e` |
| `portId` | Port Id | yes | `u` |
| `xconnectId` | Xconnect Id | no | `s` |
| `patchInfo` | Patch Info | no | `s` |
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
* Circuit Id
* Side
* Port Id
* Xconnect Id
* Patch Info
* Cost Center
* Notes
* Effective From
* Effective To
* Labels

## Pages

* `CircuitTerminationListPage` - table, filters, bulk actions, CSV export
* `CircuitTerminationDetailPage` - read only view with status buttons
* `CircuitTerminationCreatePage` / `CircuitTerminationEditPage` - shared `CircuitTerminationForm`
