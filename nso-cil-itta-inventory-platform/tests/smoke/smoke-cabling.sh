#!/usr/bin/env bash
# Smoke test for the Cabling API. Usage: smoke-cabling.sh <base-url> [tenant]
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
check "cables" "/api/v1/cables?size=1"
check "patch-panels" "/api/v1/patch-panels?size=1"
check "patch-ports" "/api/v1/patch-ports?size=1"
check "splices" "/api/v1/splices?size=1"
check "fiber-strands" "/api/v1/fiber-strands?size=1"
exit $fail
