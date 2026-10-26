# Service Endpoint

Domain: **Services** (`service`) - ASGI app `itta_api.domains.service.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `service_id` | `serviceId` | Service Id | yes |
| `site_id` | `siteId` | Site Id | no |
| `port_id` | `portId` | Port Id | no |
| `endpoint_role` | `endpointRole` | Endpoint Role | no |
| `handoff` | `handoff` | Handoff | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/service-endpoints` search
* `GET /api/v1/service-endpoints/meta` field catalogue
* `POST /api/v1/service-endpoints/reconcile?apply=false` reconcile external records
* `GET /api/v1/service-endpoints/export` CSV
