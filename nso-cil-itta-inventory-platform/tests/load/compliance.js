import http from 'k6/http';
import { check, group, sleep } from 'k6';

export const options = {
  scenarios: {
    steady: { executor: 'constant-arrival-rate', rate: 50, timeUnit: '1s', duration: '5m', preAllocatedVUs: 50, maxVUs: 200 },
    spike: { executor: 'ramping-arrival-rate', startRate: 10, timeUnit: '1s', stages: [
      { target: 200, duration: '1m' }, { target: 10, duration: '1m' },
    ], preAllocatedVUs: 100, maxVUs: 400, startTime: '5m' },
  },
  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<800'],
  },
};

const BASE = __ENV.BASE_URL;
const headers = { 'X-Tenant-Id': __ENV.TENANT || 'uniti' };

export default function () {
  group('Compliance Rule', () => {
    const list = http.get(`${BASE}/api/v1/compliance-rules?size=20`, { headers });
    check(list, { 'ComplianceRule list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/compliance-rules/stats/status`, { headers });
    check(stats, { 'ComplianceRule stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/compliance-rules/meta`, { headers });
    check(meta, { 'ComplianceRule meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Compliance Check', () => {
    const list = http.get(`${BASE}/api/v1/compliance-checks?size=20`, { headers });
    check(list, { 'ComplianceCheck list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/compliance-checks/stats/status`, { headers });
    check(stats, { 'ComplianceCheck stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/compliance-checks/meta`, { headers });
    check(meta, { 'ComplianceCheck meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Audit Record', () => {
    const list = http.get(`${BASE}/api/v1/audit-records?size=20`, { headers });
    check(list, { 'AuditRecord list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/audit-records/stats/status`, { headers });
    check(stats, { 'AuditRecord stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/audit-records/meta`, { headers });
    check(meta, { 'AuditRecord meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Compliance Exception', () => {
    const list = http.get(`${BASE}/api/v1/compliance-exceptions?size=20`, { headers });
    check(list, { 'ComplianceException list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/compliance-exceptions/stats/status`, { headers });
    check(stats, { 'ComplianceException stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/compliance-exceptions/meta`, { headers });
    check(meta, { 'ComplianceException meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Policy Document', () => {
    const list = http.get(`${BASE}/api/v1/policy-documents?size=20`, { headers });
    check(list, { 'PolicyDocument list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/policy-documents/stats/status`, { headers });
    check(stats, { 'PolicyDocument stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/policy-documents/meta`, { headers });
    check(meta, { 'PolicyDocument meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
