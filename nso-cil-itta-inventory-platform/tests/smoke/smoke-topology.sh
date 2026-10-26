#!/usr/bin/env bash
# Smoke test for the Topology and Links API. Usage: smoke-topology.sh <base-url> [tenant]
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
check "links" "/api/v1/links?size=1"
check "topology-nodes" "/api/v1/topology-nodes?size=1"
check "topology-edges" "/api/v1/topology-edges?size=1"
check "topology-views" "/api/v1/topology-views?size=1"
check "path-traces" "/api/v1/path-traces?size=1"
exit $fail
