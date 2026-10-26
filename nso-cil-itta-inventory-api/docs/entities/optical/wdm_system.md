# Wdm System

Domain: **Optical** (`optical`) - ASGI app `itta_api.domains.optical.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `system_name` | `systemName` | System Name | yes |
| `channel_capacity` | `channelCapacity` | Channel Capacity | yes |
| `band` | `band` | Band | no |
| `span_count` | `spanCount` | Span Count | no |
| `vendor_id` | `vendorId` | Vendor Id | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |

## Routes

* `GET /api/v1/wdm-systems` search
* `GET /api/v1/wdm-systems/meta` field catalogue
* `POST /api/v1/wdm-systems/reconcile?apply=false` reconcile external records
* `GET /api/v1/wdm-systems/export` CSV
