# Circuit Segment feature

Route prefix `/circuit/circuit-segments` - folder `src/features/circuit/circuit-segment`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `circuitId` | Circuit Id | yes | `u` |
| `segmentOrder` | Segment Order | yes | `i` |
| `aTerminationId` | A Termination Id | no | `u` |
| `zTerminationId` | Z Termination Id | no | `u` |
| `latencyMs` | Latency Ms | no | `d` |
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
* Circuit Id
* Segment Order
* A Termination Id
* Z Termination Id
* Latency Ms
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `CircuitSegmentListPage` - table, filters, bulk actions, CSV export
* `CircuitSegmentDetailPage` - read only view with status buttons
* `CircuitSegmentCreatePage` / `CircuitSegmentEditPage` - shared `CircuitSegmentForm`
