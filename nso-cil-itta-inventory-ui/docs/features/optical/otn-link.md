# Otn Link feature

Route prefix `/optical/otn-links` - folder `src/features/optical/otn-link`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `aNodeId` | A Node Id | yes | `u` |
| `zNodeId` | Z Node Id | yes | `u` |
| `odu` | Odu | no | `e` |
| `latencyUs` | Latency Us | no | `d` |
| `protection` | Protection | no | `s` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* A Node Id
* Z Node Id
* Odu
* Latency Us
* Protection
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Region

## Pages

* `OtnLinkListPage` - table, filters, bulk actions, CSV export
* `OtnLinkDetailPage` - read only view with status buttons
* `OtnLinkCreatePage` / `OtnLinkEditPage` - shared `OtnLinkForm`
