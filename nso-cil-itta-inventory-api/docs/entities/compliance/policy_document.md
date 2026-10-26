# Policy Document

Domain: **Compliance and Audit** (`compliance`) - ASGI app `itta_api.domains.compliance.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `title` | `title` | Title | yes |
| `doc_version` | `docVersion` | Doc Version | yes |
| `effective_date` | `effectiveDate` | Effective Date | yes |
| `owner` | `owner` | Owner | no |
| `document_url` | `documentUrl` | Document Url | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |

## Routes

* `GET /api/v1/policy-documents` search
* `GET /api/v1/policy-documents/meta` field catalogue
* `POST /api/v1/policy-documents/reconcile?apply=false` reconcile external records
* `GET /api/v1/policy-documents/export` CSV
