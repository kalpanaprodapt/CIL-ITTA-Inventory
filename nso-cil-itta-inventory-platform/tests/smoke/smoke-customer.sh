#!/usr/bin/env bash
# Smoke test for the Customers and SLA API. Usage: smoke-customer.sh <base-url> [tenant]
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
check "customers" "/api/v1/customers?size=1"
check "customer-accounts" "/api/v1/customer-accounts?size=1"
check "customer-contacts" "/api/v1/customer-contacts?size=1"
check "contracts" "/api/v1/contracts?size=1"
check "sla-profiles" "/api/v1/sla-profiles?size=1"
exit $fail
