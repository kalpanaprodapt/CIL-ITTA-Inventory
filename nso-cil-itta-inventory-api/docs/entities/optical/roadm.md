# Roadm

Domain: **Optical** (`optical`) - ASGI app `itta_api.domains.optical.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `system_id` | `systemId` | System Id | yes |
| `degrees` | `degrees` | Degrees | yes |
| `colorless` | `colorless` | Colorless | no |
| `directionless` | `directionless` | Directionless | no |
| `contentionless` | `contentionless` | Contentionless | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/roadms` search
* `GET /api/v1/roadms/meta` field catalogue
* `POST /api/v1/roadms/reconcile?apply=false` reconcile external records
* `GET /api/v1/roadms/export` CSV
