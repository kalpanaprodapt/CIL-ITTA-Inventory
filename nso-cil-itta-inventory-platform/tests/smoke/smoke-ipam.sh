#!/usr/bin/env bash
# Smoke test for the IP Management API. Usage: smoke-ipam.sh <base-url> [tenant]
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
check "ip-subnets" "/api/v1/ip-subnets?size=1"
check "ip-addresses" "/api/v1/ip-addresses?size=1"
check "ip-pools" "/api/v1/ip-pools?size=1"
check "vrf-instances" "/api/v1/vrf-instances?size=1"
check "dns-records" "/api/v1/dns-records?size=1"
exit $fail
