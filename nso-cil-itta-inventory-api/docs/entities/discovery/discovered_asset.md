# Discovered Asset

Domain: **Discovery and Reconciliation** (`discovery`) - ASGI app `itta_api.domains.discovery.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `job_id` | `jobId` | Job Id | yes |
| `asset_type` | `assetType` | Asset Type | yes |
| `fingerprint` | `fingerprint` | Fingerprint | yes |
| `raw_payload` | `rawPayload` | Raw Payload | no |
| `matched_asset_id` | `matchedAssetId` | Matched Asset Id | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/discovered-assets` search
* `GET /api/v1/discovered-assets/meta` field catalogue
* `POST /api/v1/discovered-assets/reconcile?apply=false` reconcile external records
* `GET /api/v1/discovered-assets/export` CSV
