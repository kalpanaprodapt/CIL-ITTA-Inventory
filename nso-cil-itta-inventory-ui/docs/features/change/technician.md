# Technician feature

Route prefix `/change/technicians` - folder `src/features/change/technician`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `employeeId` | Employee Id | yes | `s` |
| `fullName` | Full Name | yes | `s` |
| `skillSet` | Skill Set | no | `s` |
| `homeSiteId` | Home Site Id | no | `u` |
| `certificationLevel` | Certification Level | no | `i` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Employee Id
* Full Name
* Skill Set
* Home Site Id
* Certification Level
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `TechnicianListPage` - table, filters, bulk actions, CSV export
* `TechnicianDetailPage` - read only view with status buttons
* `TechnicianCreatePage` / `TechnicianEditPage` - shared `TechnicianForm`
