# K8s Cluster

Domain: **Virtual and Cloud** (`virtual`) - ASGI app `itta_api.domains.virtual.app:app`

| Python | Wire name | Label | Required |
|--------|-----------|-------|----------|
| `code` | `code` | Code | yes |
| `name` | `name` | Name | yes |
| `description` | `description` | Description | no |
| `status` | `status` | Status | yes |
| `external_ref` | `externalRef` | External Ref | no |
| `cluster_name` | `clusterName` | Cluster Name | yes |
| `cluster_version` | `clusterVersion` | Cluster Version | yes |
| `node_count` | `nodeCount` | Node Count | yes |
| `cloud_account_id` | `cloudAccountId` | Cloud Account Id | no |
| `region` | `region` | Region | no |
| `api_endpoint` | `apiEndpoint` | Api Endpoint | no |
| `effective_to` | `effectiveTo` | Effective To | no |
| `labels` | `labels` | Labels | no |
| `lifecycle_stage` | `lifecycleStage` | Lifecycle Stage | no |
| `source_system` | `sourceSystem` | Source System | no |
| `owner` | `owner` | Owner | no |

## Routes

* `GET /api/v1/k8s-clusters` search
* `GET /api/v1/k8s-clusters/meta` field catalogue
* `POST /api/v1/k8s-clusters/reconcile?apply=false` reconcile external records
* `GET /api/v1/k8s-clusters/export` CSV
