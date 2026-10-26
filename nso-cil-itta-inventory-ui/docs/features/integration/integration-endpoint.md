# Integration Endpoint feature

Route prefix `/integration/integration-endpoints` - folder `src/features/integration/integration-endpoint`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `endpointName` | Endpoint Name | yes | `s` |
| `targetSystem` | Target System | yes | `s` |
| `baseUrl` | Base Url | yes | `s` |
| `authType` | Auth Type | no | `e` |
| `timeoutMs` | Timeout Ms | no | `i` |
| `sourceSystem` | Source System | no | `s` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Endpoint Name
* Target System
* Base Url
* Auth Type
* Timeout Ms
* Source System
* Region
* Owner
* Criticality
* Cost Center

## Pages

* `IntegrationEndpointListPage` - table, filters, bulk actions, CSV export
* `IntegrationEndpointDetailPage` - read only view with status buttons
* `IntegrationEndpointCreatePage` / `IntegrationEndpointEditPage` - shared `IntegrationEndpointForm`
