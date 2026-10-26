# Discovery Job feature

Route prefix `/discovery/discovery-jobs` - folder `src/features/discovery/discovery-job`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `profileId` | Profile Id | yes | `u` |
| `startedAt` | Started At | no | `T` |
| `finishedAt` | Finished At | no | `T` |
| `jobState` | Job State | no | `e` |
| `assetsFound` | Assets Found | no | `i` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |
| `effectiveFrom` | Effective From | no | `D` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Profile Id
* Started At
* Finished At
* Job State
* Assets Found
* Owner
* Criticality
* Cost Center
* Notes
* Effective From

## Pages

* `DiscoveryJobListPage` - table, filters, bulk actions, CSV export
* `DiscoveryJobDetailPage` - read only view with status buttons
* `DiscoveryJobCreatePage` / `DiscoveryJobEditPage` - shared `DiscoveryJobForm`
