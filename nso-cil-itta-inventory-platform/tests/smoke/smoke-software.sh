#!/usr/bin/env bash
# Smoke test for the Software and Licensing API. Usage: smoke-software.sh <base-url> [tenant]
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
check "software-images" "/api/v1/software-images?size=1"
check "software-versions" "/api/v1/software-versions?size=1"
check "patchs" "/api/v1/patchs?size=1"
check "license-keies" "/api/v1/license-keies?size=1"
check "license-pools" "/api/v1/license-pools?size=1"
exit $fail
