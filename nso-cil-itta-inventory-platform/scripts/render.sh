#!/usr/bin/env bash
# Re-render gitops manifests from the Helm chart for every workload and environment.
set -euo pipefail
cd "$(dirname "$0")/.."
for env in dev qa uat perf stage prod; do
  for values in helm/values/$env/*.yaml; do
    name=$(basename "$values" .yaml)
    mkdir -p "gitops/$env/$name"
    helm template "$name" charts/itta-workload -f "$values" --namespace "itta-inventory-$env" > "gitops/$env/$name/manifests.yaml"
  done
done
