# Service Instance

Domain: **Services** (`service`) - ASGI app `itta_api.domains.service.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `catalog_item_id` | `catalogItemId` | Catalog Item Id | yes |
| `customer_id` | `customerId` | Customer Id | yes |
| `activated_on` | `activatedOn` | Activated On | no |
| `bandwidth_mbps` | `bandwidthMbps` | Bandwidth Mbps | no |
| `service_state` | `serviceState` | Service State | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/service-instances` search
* `GET /api/v1/service-instances/meta` field catalogue
* `POST /api/v1/service-instances/reconcile?apply=false` reconcile external records
* `GET /api/v1/service-instances/export` CSV
