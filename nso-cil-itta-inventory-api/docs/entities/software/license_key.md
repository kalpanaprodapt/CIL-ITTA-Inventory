# License Key

Domain: **Software and Licensing** (`software`) - ASGI app `itta_api.domains.software.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `pool_id` | `poolId` | Pool Id | yes |
| `key_value` | `keyValue` | Key Value | yes |
| `feature` | `feature` | Feature | yes |
| `expires_on` | `expiresOn` | Expires On | no |
| `assigned_device_id` | `assignedDeviceId` | Assigned Device Id | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/license-keies` search
* `GET /api/v1/license-keies/meta` field catalogue
* `POST /api/v1/license-keies/reconcile?apply=false` reconcile external records
* `GET /api/v1/license-keies/export` CSV
