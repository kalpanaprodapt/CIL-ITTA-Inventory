# Hardware Model feature

Route prefix `/vendor/hardware-models` - folder `src/features/vendor/hardware-model`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `vendorId` | Vendor Id | yes | `u` |
| `modelNumber` | Model Number | yes | `s` |
| `formFactor` | Form Factor | no | `s` |
| `powerWatts` | Power Watts | no | `i` |
| `eolDate` | Eol Date | no | `D` |
| `eosDate` | Eos Date | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Vendor Id
* Model Number
* Form Factor
* Power Watts
* Eol Date
* Eos Date
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `HardwareModelListPage` - table, filters, bulk actions, CSV export
* `HardwareModelDetailPage` - read only view with status buttons
* `HardwareModelCreatePage` / `HardwareModelEditPage` - shared `HardwareModelForm`
