# Amplifier feature

Route prefix `/optical/amplifiers` - folder `src/features/optical/amplifier`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `systemId` | System Id | yes | `u` |
| `ampType` | Amp Type | no | `e` |
| `gainDb` | Gain Db | yes | `d` |
| `outputPowerDbm` | Output Power Dbm | no | `d` |
| `noiseFigureDb` | Noise Figure Db | no | `d` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* System Id
* Amp Type
* Gain Db
* Output Power Dbm
* Noise Figure Db
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `AmplifierListPage` - table, filters, bulk actions, CSV export
* `AmplifierDetailPage` - read only view with status buttons
* `AmplifierCreatePage` / `AmplifierEditPage` - shared `AmplifierForm`
