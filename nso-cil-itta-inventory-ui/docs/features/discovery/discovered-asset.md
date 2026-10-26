# Discovered Asset feature

Route prefix `/discovery/discovered-assets` - folder `src/features/discovery/discovered-asset`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `jobId` | Job Id | yes | `u` |
| `assetType` | Asset Type | yes | `s` |
| `fingerprint` | Fingerprint | yes | `s` |
| `rawPayload` | Raw Payload | no | `t` |
| `matchedAssetId` | Matched Asset Id | no | `u` |
| `effectiveFrom` | Effective From | no | `D` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Job Id
* Asset Type
* Fingerprint
* Raw Payload
* Matched Asset Id
* Effective From
* Effective To
* Labels
* Lifecycle Stage
* Source System

## Pages

* `DiscoveredAssetListPage` - table, filters, bulk actions, CSV export
* `DiscoveredAssetDetailPage` - read only view with status buttons
* `DiscoveredAssetCreatePage` / `DiscoveredAssetEditPage` - shared `DiscoveredAssetForm`
