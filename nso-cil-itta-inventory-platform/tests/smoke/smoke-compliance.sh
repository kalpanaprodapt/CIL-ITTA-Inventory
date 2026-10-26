#!/usr/bin/env bash
# Smoke test for the Compliance and Audit API. Usage: smoke-compliance.sh <base-url> [tenant]
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
check "compliance-rules" "/api/v1/compliance-rules?size=1"
check "compliance-checks" "/api/v1/compliance-checks?size=1"
check "audit-records" "/api/v1/audit-records?size=1"
check "compliance-exceptions" "/api/v1/compliance-exceptions?size=1"
check "policy-documents" "/api/v1/policy-documents?size=1"
exit $fail
