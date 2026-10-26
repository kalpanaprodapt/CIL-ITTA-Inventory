# Integration Endpoint

Domain: **Integration Layer** (`integration`) - ASGI app `itta_api.domains.integration.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `endpoint_name` | `endpointName` | Endpoint Name | yes |
| `target_system` | `targetSystem` | Target System | yes |
| `base_url` | `baseUrl` | Base Url | yes |
| `auth_type` | `authType` | Auth Type | no |
| `timeout_ms` | `timeoutMs` | Timeout Ms | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/integration-endpoints` search
* `GET /api/v1/integration-endpoints/meta` field catalogue
* `POST /api/v1/integration-endpoints/reconcile?apply=false` reconcile external records
* `GET /api/v1/integration-endpoints/export` CSV
