#!/usr/bin/env bash
# Smoke test for the Asset and Finance API. Usage: smoke-assetmgmt.sh <base-url> [tenant]
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
check "assets" "/api/v1/assets?size=1"
check "asset-tags" "/api/v1/asset-tags?size=1"
check "purchase-orders" "/api/v1/purchase-orders?size=1"
check "invoices" "/api/v1/invoices?size=1"
check "depreciation-schedules" "/api/v1/depreciation-schedules?size=1"
exit $fail
