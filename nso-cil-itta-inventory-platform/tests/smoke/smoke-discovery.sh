#!/usr/bin/env bash
# Smoke test for the Discovery and Reconciliation API. Usage: smoke-discovery.sh <base-url> [tenant]
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
check "discovery-jobs" "/api/v1/discovery-jobs?size=1"
check "discovery-profiles" "/api/v1/discovery-profiles?size=1"
check "discovered-assets" "/api/v1/discovered-assets?size=1"
check "reconciliation-rules" "/api/v1/reconciliation-rules?size=1"
check "reconciliation-results" "/api/v1/reconciliation-results?size=1"
exit $fail
