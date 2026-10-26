#!/usr/bin/env bash
# Smoke test for the Change and Work Management API. Usage: smoke-change.sh <base-url> [tenant]
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
check "change-requests" "/api/v1/change-requests?size=1"
check "maintenance-windows" "/api/v1/maintenance-windows?size=1"
check "work-orders" "/api/v1/work-orders?size=1"
check "field-tasks" "/api/v1/field-tasks?size=1"
check "technicians" "/api/v1/technicians?size=1"
exit $fail
