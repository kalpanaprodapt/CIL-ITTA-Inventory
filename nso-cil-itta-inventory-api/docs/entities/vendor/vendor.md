# Vendor

Domain: **Vendor and Model** (`vendor`) - ASGI app `itta_api.domains.vendor.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `legal_name` | `legalName` | Legal Name | yes |
| `support_email` | `supportEmail` | Support Email | no |
| `support_phone` | `supportPhone` | Support Phone | no |
| `website` | `website` | Website | no |
| `tier` | `tier` | Tier | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |

## Routes

* `GET /api/v1/vendors` search
* `GET /api/v1/vendors/meta` field catalogue
* `POST /api/v1/vendors/reconcile?apply=false` reconcile external records
* `GET /api/v1/vendors/export` CSV
