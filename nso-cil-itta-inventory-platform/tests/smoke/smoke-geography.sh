#!/usr/bin/env bash
# Smoke test for the Geography API. Usage: smoke-geography.sh <base-url> [tenant]
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
check "countries" "/api/v1/countries?size=1"
check "regions" "/api/v1/regions?size=1"
check "cities" "/api/v1/cities?size=1"
check "sites" "/api/v1/sites?size=1"
check "buildings" "/api/v1/buildings?size=1"
exit $fail
