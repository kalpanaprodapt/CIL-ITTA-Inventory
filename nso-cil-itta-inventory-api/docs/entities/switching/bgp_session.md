# Bgp Session

Domain: **L2 and L3 Routing** (`switching`) - ASGI app `itta_api.domains.switching.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `local_device_id` | `localDeviceId` | Local Device Id | yes |
| `peer_address` | `peerAddress` | Peer Address | yes |
| `local_asn` | `localAsn` | Local Asn | yes |
| `remote_asn` | `remoteAsn` | Remote Asn | yes |
| `session_state` | `sessionState` | Session State | no |
| `prefix_limit` | `prefixLimit` | Prefix Limit | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |
| `criticality` | `criticality` | Criticality | no |
| `cost_center` | `costCenter` | Cost Center | no |
| `notes` | `notes` | Notes | no |

## Routes

* `GET /api/v1/bgp-sessions` search
* `GET /api/v1/bgp-sessions/meta` field catalogue
* `POST /api/v1/bgp-sessions/reconcile?apply=false` reconcile external records
* `GET /api/v1/bgp-sessions/export` CSV
