#!/usr/bin/env bash
# Smoke test for the Alarms and Events API. Usage: smoke-alarm.sh <base-url> [tenant]
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
check "alarm-definitions" "/api/v1/alarm-definitions?size=1"
check "alarm-instances" "/api/v1/alarm-instances?size=1"
check "event-sources" "/api/v1/event-sources?size=1"
check "suppression-rules" "/api/v1/suppression-rules?size=1"
check "ticket-links" "/api/v1/ticket-links?size=1"
exit $fail
