#!/usr/bin/env bash
# Smoke test for the Radio and Mobile API. Usage: smoke-radio.sh <base-url> [tenant]
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
check "radio-units" "/api/v1/radio-units?size=1"
check "base-stations" "/api/v1/base-stations?size=1"
check "antennas" "/api/v1/antennas?size=1"
check "sectors" "/api/v1/sectors?size=1"
check "backhaul-links" "/api/v1/backhaul-links?size=1"
exit $fail
