# Environments

| Environment | Cluster | Replicas | Approval |
|-------------|---------|----------|----------|
| dev | aks-cil-nonprod-eus2 | 1 | none |
| qa | aks-cil-nonprod-eus2 | 1 | none |
| uat | aks-cil-nonprod-eus2 | 2 | uat approvers |
| perf | aks-cil-perf-cus | 3 | perf approvers |
| stage | aks-cil-stage-eus2 | 2 | release managers |
| prod | aks-cil-prod-eus2 | 3 | change advisory board |

Promotion order is dev -> qa -> uat -> perf -> stage -> prod. Each stage deploys through Argo CD and runs a smoke test.
