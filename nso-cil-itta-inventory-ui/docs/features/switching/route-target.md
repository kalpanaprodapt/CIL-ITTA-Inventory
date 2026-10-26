# Route Target feature

Route prefix `/switching/route-targets` - folder `src/features/switching/route-target`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `asn` | Asn | yes | `l` |
| `assignedNumber` | Assigned Number | yes | `l` |
| `rtType` | Rt Type | no | `e` |
| `vrfId` | Vrf Id | no | `u` |
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
* Asn
* Assigned Number
* Rt Type
* Vrf Id
* Lifecycle Stage
* Source System
* Region
* Owner
* Criticality

## Pages

* `RouteTargetListPage` - table, filters, bulk actions, CSV export
* `RouteTargetDetailPage` - read only view with status buttons
* `RouteTargetCreatePage` / `RouteTargetEditPage` - shared `RouteTargetForm`
