#!/usr/bin/env bash
# Smoke test for the Services API. Usage: smoke-service.sh <base-url> [tenant]
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
check "service-catalog-items" "/api/v1/service-catalog-items?size=1"
check "service-instances" "/api/v1/service-instances?size=1"
check "service-orders" "/api/v1/service-orders?size=1"
check "service-dependencies" "/api/v1/service-dependencies?size=1"
check "service-endpoints" "/api/v1/service-endpoints?size=1"
exit $fail
