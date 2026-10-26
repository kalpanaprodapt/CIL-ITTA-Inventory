#!/usr/bin/env bash
# Smoke test for the Virtual and Cloud API. Usage: smoke-virtual.sh <base-url> [tenant]
set -euo pipefail
BASE="${1:?base url required}"
TENANT="${2:-uniti}"
fail=0

check() {
  local name="$1" path="$2"
  local code
  code=$(curl -s -o /dev/null -w '%{http_code}' --max-time 10 -H "X-Tenant-Id: $TENANT" "$BASE$path" || true)
  if [ "$code" = "200" ]; then
    echo "ok   $name"
  else
    echo "FAIL $name ($code)"
    fail=1
  fi
}

check health "/healthz"
check "vnfs" "/api/v1/vnfs?size=1"
check "hypervisors" "/api/v1/hypervisors?size=1"
check "virtual-machines" "/api/v1/virtual-machines?size=1"
check "cloud-accounts" "/api/v1/cloud-accounts?size=1"
check "k8s-clusters" "/api/v1/k8s-clusters?size=1"
exit $fail
