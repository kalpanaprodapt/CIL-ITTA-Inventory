#!/usr/bin/env bash
# Smoke test for the Port and Interface API. Usage: smoke-port.sh <base-url> [tenant]
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
check "ports" "/api/v1/ports?size=1"
check "interfaces" "/api/v1/interfaces?size=1"
check "sub-interfaces" "/api/v1/sub-interfaces?size=1"
check "lag-groups" "/api/v1/lag-groups?size=1"
check "transceivers" "/api/v1/transceivers?size=1"
exit $fail
