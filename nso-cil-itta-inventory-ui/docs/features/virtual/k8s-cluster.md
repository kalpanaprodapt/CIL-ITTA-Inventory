# K8s Cluster feature

Route prefix `/virtual/k8s-clusters` - folder `src/features/virtual/k8s-cluster`

## Fields

| Attribute | Label | Required | Kind |
|-----------|-------|----------|------|
| `code` | Code | yes | `s` |
| `name` | Name | yes | `s` |
| `description` | Description | no | `t` |
| `status` | Status | yes | `e` |
| `externalRef` | External Ref | no | `s` |
| `clusterName` | Cluster Name | yes | `s` |
| `clusterVersion` | Cluster Version | yes | `s` |
| `nodeCount` | Node Count | yes | `i` |
| `cloudAccountId` | Cloud Account Id | no | `u` |
| `region` | Region | no | `s` |
| `apiEndpoint` | Api Endpoint | no | `s` |
| `effectiveTo` | Effective To | no | `D` |
| `labels` | Labels | no | `s` |
| `lifecycleStage` | Lifecycle Stage | no | `e` |
| `sourceSystem` | Source System | no | `s` |
| `owner` | Owner | no | `s` |

## Filters

* Free text search
* Status
* Code
* Name
* Description
* External Ref
* Cluster Name
* Cluster Version
* Node Count
* Cloud Account Id
* Region
* Api Endpoint
* Effective To
* Labels
* Lifecycle Stage
* Source System
* Owner

## Pages

* `K8sClusterListPage` - table, filters, bulk actions, CSV export
* `K8sClusterDetailPage` - read only view with status buttons
* `K8sClusterCreatePage` / `K8sClusterEditPage` - shared `K8sClusterForm`
