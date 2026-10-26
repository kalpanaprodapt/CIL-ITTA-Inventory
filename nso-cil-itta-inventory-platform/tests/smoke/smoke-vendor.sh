#!/usr/bin/env bash
# Smoke test for the Vendor and Model API. Usage: smoke-vendor.sh <base-url> [tenant]
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
check "vendors" "/api/v1/vendors?size=1"
check "hardware-models" "/api/v1/hardware-models?size=1"
check "skus" "/api/v1/skus?size=1"
check "support-contracts" "/api/v1/support-contracts?size=1"
check "warranties" "/api/v1/warranties?size=1"
exit $fail
