# Building

Domain: **Geography** (`geography`) - ASGI app `itta_api.domains.geography.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `site_id` | `siteId` | Site Id | yes |
| `floors` | `floors` | Floors | no |
| `year_built` | `yearBuilt` | Year Built | no |
| `ownership` | `ownership` | Ownership | no |
| `lease_expiry` | `leaseExpiry` | Lease Expiry | no |
| `security_level` | `securityLevel` | Security Level | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |

## Routes

* `GET /api/v1/buildings` search
* `GET /api/v1/buildings/meta` field catalogue
* `POST /api/v1/buildings/reconcile?apply=false` reconcile external records
* `GET /api/v1/buildings/export` CSV
