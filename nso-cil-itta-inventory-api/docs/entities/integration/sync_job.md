# Sync Job

Domain: **Integration Layer** (`integration`) - ASGI app `itta_api.domains.integration.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `endpoint_id` | `endpointId` | Endpoint Id | yes |
| `direction` | `direction` | Direction | no |
| `schedule_cron` | `scheduleCron` | Schedule Cron | no |
| `last_run_at` | `lastRunAt` | Last Run At | no |
| `last_status` | `lastStatus` | Last Status | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |

## Routes

* `GET /api/v1/sync-jobs` search
* `GET /api/v1/sync-jobs/meta` field catalogue
* `POST /api/v1/sync-jobs/reconcile?apply=false` reconcile external records
* `GET /api/v1/sync-jobs/export` CSV
