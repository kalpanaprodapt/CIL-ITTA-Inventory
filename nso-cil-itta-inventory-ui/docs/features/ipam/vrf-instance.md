# Vrf Instance feature

Route prefix `/ipam/vrf-instances` - folder `src/features/ipam/vrf-instance`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `vrfName` | Vrf Name | yes | `s` |
| `routeDistinguisher` | Route Distinguisher | yes | `s` |
| `importTargets` | Import Targets | no | `s` |
| `exportTargets` | Export Targets | no | `s` |
| `deviceId` | Device Id | no | `u` |
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
* Vrf Name
* Route Distinguisher
* Import Targets
* Export Targets
* Device Id
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `VrfInstanceListPage` - table, filters, bulk actions, CSV export
* `VrfInstanceDetailPage` - read only view with status buttons
* `VrfInstanceCreatePage` / `VrfInstanceEditPage` - shared `VrfInstanceForm`
