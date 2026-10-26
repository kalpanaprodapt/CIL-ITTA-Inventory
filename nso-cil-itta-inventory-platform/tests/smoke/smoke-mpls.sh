#!/usr/bin/env bash
# Smoke test for the MPLS and VPN API. Usage: smoke-mpls.sh <base-url> [tenant]
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
check "mpls-lsps" "/api/v1/mpls-lsps?size=1"
check "vpn-services" "/api/v1/vpn-services?size=1"
check "pseudo-wires" "/api/v1/pseudo-wires?size=1"
check "tunnel-endpoints" "/api/v1/tunnel-endpoints?size=1"
check "qos-policies" "/api/v1/qos-policies?size=1"
exit $fail
