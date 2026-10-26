# Discovery Profile

Domain: **Discovery and Reconciliation** (`discovery`) - ASGI app `itta_api.domains.discovery.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `profile_name` | `profileName` | Profile Name | yes |
| `protocol` | `protocol` | Protocol | no |
| `credential_ref` | `credentialRef` | Credential Ref | no |
| `scope_cidr` | `scopeCidr` | Scope Cidr | no |
| `schedule_cron` | `scheduleCron` | Schedule Cron | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |

## Routes

* `GET /api/v1/discovery-profiles` search
* `GET /api/v1/discovery-profiles/meta` field catalogue
* `POST /api/v1/discovery-profiles/reconcile?apply=false` reconcile external records
* `GET /api/v1/discovery-profiles/export` CSV
