# Service Endpoint feature

Route prefix `/service/service-endpoints` - folder `src/features/service/service-endpoint`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `serviceId` | Service Id | yes | `u` |
| `siteId` | Site Id | no | `u` |
| `portId` | Port Id | no | `u` |
| `endpointRole` | Endpoint Role | no | `e` |
| `handoff` | Handoff | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Service Id
* Site Id
* Port Id
* Endpoint Role
* Handoff
* Notes
* Effective From
* Effective To
* Labels
* Lifecycle Stage

## Pages

* `ServiceEndpointListPage` - table, filters, bulk actions, CSV export
* `ServiceEndpointDetailPage` - read only view with status buttons
* `ServiceEndpointCreatePage` / `ServiceEndpointEditPage` - shared `ServiceEndpointForm`
