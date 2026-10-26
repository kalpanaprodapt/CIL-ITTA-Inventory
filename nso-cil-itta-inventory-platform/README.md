# nso-cil-itta-inventory-platform

Delivery platform for the Uniti **CIL ITTA network inventory**: container images, Kubernetes manifests
(Helm values + rendered GitOps manifests + Argo CD applications), Azure DevOps pipelines and Terraform.

| Path | Content |
|------|---------|
| `docker/` | One Dockerfile per workload, nginx config, local `docker-compose.yml` |
| `charts/itta-workload` | Generic Helm chart (Deployment, Service, HPA, ...) |
| `helm/values/<env>/<workload>.yaml` | Per environment values |
| `gitops/<env>/<workload>/manifests.yaml` | Rendered manifests (`scripts/render.sh`) |
| `argocd/` | Projects and Applications per environment |
| `pipelines/` | Azure DevOps CI per workload, CD per workload, shared templates, variable groups |
| `infra/terraform/` | AKS, ACR, Key Vault, PostgreSQL, Event Hubs, monitoring, workload identities |
| `observability/` | Grafana dashboards and Prometheus SLO rules per domain |
| `policies/` | Kyverno guard rails |
| `tests/` | Smoke tests and k6 load tests per domain |
| `docs/runbooks/` | Operational runbooks per domain |

Environments: dev, qa, uat, perf, stage, prod. Workloads (49):

| Workload | Kind | Domain |
|----------|------|--------|
| inventory-geography-service | java | geography |
| inventory-facility-service | java | facility |
| inventory-device-service | java | device |
| inventory-port-service | java | port |
| inventory-cabling-service | java | cabling |
| inventory-vendor-service | java | vendor |
| inventory-software-service | java | software |
| inventory-ipam-service | java | ipam |
| inventory-switching-service | java | switching |
| inventory-circuit-service | java | circuit |
| inventory-topology-service | java | topology |
| inventory-customer-service | java | customer |
| inventory-service-service | java | service |
| inventory-mpls-service | java | mpls |
| inventory-optical-service | java | optical |
| inventory-radio-service | java | radio |
| inventory-virtual-service | java | virtual |
| inventory-discovery-service | java | discovery |
| inventory-change-service | java | change |
| inventory-assetmgmt-service | java | assetmgmt |
| inventory-compliance-service | java | compliance |
| inventory-alarm-service | java | alarm |
| inventory-capacity-service | java | capacity |
| inventory-integration-service | java | integration |
| inventory-geography-api | python | geography |
| inventory-facility-api | python | facility |
| inventory-device-api | python | device |
| inventory-port-api | python | port |
| inventory-cabling-api | python | cabling |
| inventory-vendor-api | python | vendor |
| inventory-software-api | python | software |
| inventory-ipam-api | python | ipam |
| inventory-switching-api | python | switching |
| inventory-circuit-api | python | circuit |
| inventory-topology-api | python | topology |
| inventory-customer-api | python | customer |
| inventory-service-api | python | service |
| inventory-mpls-api | python | mpls |
| inventory-optical-api | python | optical |
| inventory-radio-api | python | radio |
| inventory-virtual-api | python | virtual |
| inventory-discovery-api | python | discovery |
| inventory-change-api | python | change |
| inventory-assetmgmt-api | python | assetmgmt |
| inventory-compliance-api | python | compliance |
| inventory-alarm-api | python | alarm |
| inventory-capacity-api | python | capacity |
| inventory-integration-api | python | integration |
| inventory-ui | ui | ui |
