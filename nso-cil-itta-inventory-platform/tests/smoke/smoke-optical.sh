#!/usr/bin/env bash
# Smoke test for the Optical API. Usage: smoke-optical.sh <base-url> [tenant]
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
check "optical-channels" "/api/v1/optical-channels?size=1"
check "wdm-systems" "/api/v1/wdm-systems?size=1"
check "otn-links" "/api/v1/otn-links?size=1"
check "amplifiers" "/api/v1/amplifiers?size=1"
check "roadms" "/api/v1/roadms?size=1"
exit $fail
