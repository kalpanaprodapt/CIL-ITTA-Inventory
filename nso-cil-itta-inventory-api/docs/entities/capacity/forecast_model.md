# Forecast Model

Domain: **Capacity Planning** (`capacity`) - ASGI app `itta_api.domains.capacity.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `model_name` | `modelName` | Model Name | yes |
| `algorithm` | `algorithm` | Algorithm | no |
| `trained_on` | `trainedOn` | Trained On | no |
| `mape` | `mape` | Mape | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |
| `effective_from` | `effectiveFrom` | Effective From | no |
| `effective_to` | `effectiveTo` | Effective To | no |

## Routes

* `GET /api/v1/forecast-models` search
* `GET /api/v1/forecast-models/meta` field catalogue
* `POST /api/v1/forecast-models/reconcile?apply=false` reconcile external records
* `GET /api/v1/forecast-models/export` CSV
