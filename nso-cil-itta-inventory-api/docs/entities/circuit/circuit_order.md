# Circuit Order

Domain: **Circuits** (`circuit`) - ASGI app `itta_api.domains.circuit.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `circuit_id` | `circuitId` | Circuit Id | no |
| `order_number` | `orderNumber` | Order Number | yes |
| `ordered_on` | `orderedOn` | Ordered On | yes |
| `requested_date` | `requestedDate` | Requested Date | no |
| `committed_date` | `committedDate` | Committed Date | no |
| `order_status` | `orderStatus` | Order Status | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/circuit-orders` search
* `GET /api/v1/circuit-orders/meta` field catalogue
* `POST /api/v1/circuit-orders/reconcile?apply=false` reconcile external records
* `GET /api/v1/circuit-orders/export` CSV
