# Discovery Job

Domain: **Discovery and Reconciliation** (`discovery`) - ASGI app `itta_api.domains.discovery.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `profile_id` | `profileId` | Profile Id | yes |
| `started_at` | `startedAt` | Started At | no |
| `finished_at` | `finishedAt` | Finished At | no |
| `job_state` | `jobState` | Job State | no |
| `assets_found` | `assetsFound` | Assets Found | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/discovery-jobs` search
* `GET /api/v1/discovery-jobs/meta` field catalogue
* `POST /api/v1/discovery-jobs/reconcile?apply=false` reconcile external records
* `GET /api/v1/discovery-jobs/export` CSV
