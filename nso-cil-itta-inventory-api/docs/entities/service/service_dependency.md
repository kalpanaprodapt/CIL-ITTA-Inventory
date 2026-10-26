# Service Dependency

Domain: **Services** (`service`) - ASGI app `itta_api.domains.service.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `service_id` | `serviceId` | Service Id | yes |
| `depends_on_id` | `dependsOnId` | Depends On Id | yes |
| `dependency_type` | `dependencyType` | Dependency Type | no |
| `impact_score` | `impactScore` | Impact Score | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/service-dependencies` search
* `GET /api/v1/service-dependencies/meta` field catalogue
* `POST /api/v1/service-dependencies/reconcile?apply=false` reconcile external records
* `GET /api/v1/service-dependencies/export` CSV
