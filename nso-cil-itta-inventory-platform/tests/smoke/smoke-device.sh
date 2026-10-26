#!/usr/bin/env bash
# Smoke test for the Network Device API. Usage: smoke-device.sh <base-url> [tenant]
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
check "network-devices" "/api/v1/network-devices?size=1"
check "chassises" "/api/v1/chassises?size=1"
check "line-cards" "/api/v1/line-cards?size=1"
check "power-supplies" "/api/v1/power-supplies?size=1"
check "fan-traies" "/api/v1/fan-traies?size=1"
exit $fail
