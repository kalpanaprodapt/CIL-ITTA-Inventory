# Bgp Session feature

Route prefix `/switching/bgp-sessions` - folder `src/features/switching/bgp-session`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `localDeviceId` | Local Device Id | yes | `u` |
| `peerAddress` | Peer Address | yes | `s` |
| `localAsn` | Local Asn | yes | `l` |
| `remoteAsn` | Remote Asn | yes | `l` |
| `sessionState` | Session State | no | `e` |
| `prefixLimit` | Prefix Limit | no | `i` |
| `region` | Region | no | `s` |
| `owner` | Owner | no | `s` |
| `criticality` | Criticality | no | `e` |
| `costCenter` | Cost Center | no | `s` |
| `notes` | Notes | no | `t` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Local Device Id
* Peer Address
* Local Asn
* Remote Asn
* Session State
* Prefix Limit
* Region
* Owner
* Criticality
* Cost Center
* Notes

## Pages

* `BgpSessionListPage` - table, filters, bulk actions, CSV export
* `BgpSessionDetailPage` - read only view with status buttons
* `BgpSessionCreatePage` / `BgpSessionEditPage` - shared `BgpSessionForm`
