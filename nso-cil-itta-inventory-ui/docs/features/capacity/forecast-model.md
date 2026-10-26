# Forecast Model feature

Route prefix `/capacity/forecast-models` - folder `src/features/capacity/forecast-model`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `modelName` | Model Name | yes | `s` |
| `algorithm` | Algorithm | no | `e` |
| `trainedOn` | Trained On | no | `T` |
| `mape` | Mape | no | `d` |
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
* Model Name
* Algorithm
* Trained On
* Mape
* Criticality
* Cost Center
* Notes
* Effective From
* Effective To

## Pages

* `ForecastModelListPage` - table, filters, bulk actions, CSV export
* `ForecastModelDetailPage` - read only view with status buttons
* `ForecastModelCreatePage` / `ForecastModelEditPage` - shared `ForecastModelForm`
