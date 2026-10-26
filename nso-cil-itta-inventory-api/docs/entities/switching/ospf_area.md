# Ospf Area

Domain: **L2 and L3 Routing** (`switching`) - ASGI app `itta_api.domains.switching.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `area_id` | `areaId` | Area Id | yes |
| `area_type` | `areaType` | Area Type | no |
| `device_count` | `deviceCount` | Device Count | no |
| `auth_type` | `authType` | Auth Type | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/ospf-areas` search
* `GET /api/v1/ospf-areas/meta` field catalogue
* `POST /api/v1/ospf-areas/reconcile?apply=false` reconcile external records
* `GET /api/v1/ospf-areas/export` CSV
