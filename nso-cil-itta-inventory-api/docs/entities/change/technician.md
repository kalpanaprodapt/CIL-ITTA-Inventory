# Technician

Domain: **Change and Work Management** (`change`) - ASGI app `itta_api.domains.change.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `employee_id` | `employeeId` | Employee Id | yes |
| `full_name` | `fullName` | Full Name | yes |
| `skill_set` | `skillSet` | Skill Set | no |
| `home_site_id` | `homeSiteId` | Home Site Id | no |
| `certification_level` | `certificationLevel` | Certification Level | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/technicians` search
* `GET /api/v1/technicians/meta` field catalogue
* `POST /api/v1/technicians/reconcile?apply=false` reconcile external records
* `GET /api/v1/technicians/export` CSV
