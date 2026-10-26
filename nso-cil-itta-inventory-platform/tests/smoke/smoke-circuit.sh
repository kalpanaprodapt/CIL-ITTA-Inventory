#!/usr/bin/env bash
# Smoke test for the Circuits API. Usage: smoke-circuit.sh <base-url> [tenant]
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
check "circuits" "/api/v1/circuits?size=1"
check "circuit-segments" "/api/v1/circuit-segments?size=1"
check "circuit-providers" "/api/v1/circuit-providers?size=1"
check "circuit-terminations" "/api/v1/circuit-terminations?size=1"
check "circuit-orders" "/api/v1/circuit-orders?size=1"
exit $fail
