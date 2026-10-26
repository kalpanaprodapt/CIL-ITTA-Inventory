# Service Order

Domain: **Services** (`service`) - ASGI app `itta_api.domains.service.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `customer_id` | `customerId` | Customer Id | yes |
| `order_type` | `orderType` | Order Type | no |
| `requested_on` | `requestedOn` | Requested On | yes |
| `target_date` | `targetDate` | Target Date | no |
| `priority` | `priority` | Priority | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/service-orders` search
* `GET /api/v1/service-orders/meta` field catalogue
* `POST /api/v1/service-orders/reconcile?apply=false` reconcile external records
* `GET /api/v1/service-orders/export` CSV
