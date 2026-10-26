#!/usr/bin/env bash
# Smoke test for the Capacity Planning API. Usage: smoke-capacity.sh <base-url> [tenant]
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
check "capacity-plans" "/api/v1/capacity-plans?size=1"
check "utilization-samples" "/api/v1/utilization-samples?size=1"
check "threshold-policies" "/api/v1/threshold-policies?size=1"
check "forecast-models" "/api/v1/forecast-models?size=1"
check "reservations" "/api/v1/reservations?size=1"
exit $fail
