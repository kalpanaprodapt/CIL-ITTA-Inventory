# Qos Policy feature

Route prefix `/mpls/qos-policies` - folder `src/features/mpls/qos-policy`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `policyName` | Policy Name | yes | `s` |
| `classCount` | Class Count | yes | `i` |
| `cirMbps` | Cir Mbps | no | `l` |
| `pirMbps` | Pir Mbps | no | `l` |
| `markingScheme` | Marking Scheme | no | `s` |
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
* Policy Name
* Class Count
* Cir Mbps
* Pir Mbps
* Marking Scheme
* Labels
* Lifecycle Stage
* Source System
* Region
* Owner

## Pages

* `QosPolicyListPage` - table, filters, bulk actions, CSV export
* `QosPolicyDetailPage` - read only view with status buttons
* `QosPolicyCreatePage` / `QosPolicyEditPage` - shared `QosPolicyForm`
