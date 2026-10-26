#!/usr/bin/env bash
# Smoke test for the L2 and L3 Routing API. Usage: smoke-switching.sh <base-url> [tenant]
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
check "vlans" "/api/v1/vlans?size=1"
check "vlan-pools" "/api/v1/vlan-pools?size=1"
check "route-targets" "/api/v1/route-targets?size=1"
check "bgp-sessions" "/api/v1/bgp-sessions?size=1"
check "ospf-areas" "/api/v1/ospf-areas?size=1"
exit $fail
