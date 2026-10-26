# Patch

Domain: **Software and Licensing** (`software`) - ASGI app `itta_api.domains.software.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `version_id` | `versionId` | Version Id | yes |
| `patch_id` | `patchId` | Patch Id | yes |
| `severity` | `severity` | Severity | no |
| `released_on` | `releasedOn` | Released On | no |
| `reboot_required` | `rebootRequired` | Reboot Required | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/patchs` search
* `GET /api/v1/patchs/meta` field catalogue
* `POST /api/v1/patchs/reconcile?apply=false` reconcile external records
* `GET /api/v1/patchs/export` CSV
