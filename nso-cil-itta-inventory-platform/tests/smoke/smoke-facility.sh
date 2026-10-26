#!/usr/bin/env bash
# Smoke test for the Facility API. Usage: smoke-facility.sh <base-url> [tenant]
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
check "floors" "/api/v1/floors?size=1"
check "rooms" "/api/v1/rooms?size=1"
check "racks" "/api/v1/racks?size=1"
check "rack-units" "/api/v1/rack-units?size=1"
check "power-feeds" "/api/v1/power-feeds?size=1"
exit $fail
