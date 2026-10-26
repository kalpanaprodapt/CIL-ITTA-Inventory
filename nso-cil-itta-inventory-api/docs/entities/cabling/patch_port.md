# Patch Port

Domain: **Cabling** (`cabling`) - ASGI app `itta_api.domains.cabling.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `panel_id` | `panelId` | Panel Id | yes |
| `port_number` | `portNumber` | Port Number | yes |
| `connected_cable_id` | `connectedCableId` | Connected Cable Id | no |
| `polarity` | `polarity` | Polarity | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |

## Routes

* `GET /api/v1/patch-ports` search
* `GET /api/v1/patch-ports/meta` field catalogue
* `POST /api/v1/patch-ports/reconcile?apply=false` reconcile external records
* `GET /api/v1/patch-ports/export` CSV
