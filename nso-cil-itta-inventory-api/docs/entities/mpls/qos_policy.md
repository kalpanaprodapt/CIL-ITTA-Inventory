# Qos Policy

Domain: **MPLS and VPN** (`mpls`) - ASGI app `itta_api.domains.mpls.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `policy_name` | `policyName` | Policy Name | yes |
| `class_count` | `classCount` | Class Count | yes |
| `cir_mbps` | `cirMbps` | Cir Mbps | no |
| `pir_mbps` | `pirMbps` | Pir Mbps | no |
| `marking_scheme` | `markingScheme` | Marking Scheme | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `region` | `region` | Region | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/qos-policies` search
* `GET /api/v1/qos-policies/meta` field catalogue
* `POST /api/v1/qos-policies/reconcile?apply=false` reconcile external records
* `GET /api/v1/qos-policies/export` CSV
