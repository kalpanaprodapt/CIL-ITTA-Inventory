#!/usr/bin/env bash
# Smoke test for the Integration Layer API. Usage: smoke-integration.sh <base-url> [tenant]
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
check "integration-endpoints" "/api/v1/integration-endpoints?size=1"
check "sync-jobs" "/api/v1/sync-jobs?size=1"
check "sync-mappings" "/api/v1/sync-mappings?size=1"
check "import-batchs" "/api/v1/import-batchs?size=1"
check "webhook-subscriptions" "/api/v1/webhook-subscriptions?size=1"
exit $fail
