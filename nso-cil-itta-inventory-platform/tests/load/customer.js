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
  group('Customer', () => {
    const list = http.get(`${BASE}/api/v1/customers?size=20`, { headers });
    check(list, { 'Customer list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/customers/stats/status`, { headers });
    check(stats, { 'Customer stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/customers/meta`, { headers });
    check(meta, { 'Customer meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Customer Account', () => {
    const list = http.get(`${BASE}/api/v1/customer-accounts?size=20`, { headers });
    check(list, { 'CustomerAccount list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/customer-accounts/stats/status`, { headers });
    check(stats, { 'CustomerAccount stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/customer-accounts/meta`, { headers });
    check(meta, { 'CustomerAccount meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Customer Contact', () => {
    const list = http.get(`${BASE}/api/v1/customer-contacts?size=20`, { headers });
    check(list, { 'CustomerContact list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/customer-contacts/stats/status`, { headers });
    check(stats, { 'CustomerContact stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/customer-contacts/meta`, { headers });
    check(meta, { 'CustomerContact meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Contract', () => {
    const list = http.get(`${BASE}/api/v1/contracts?size=20`, { headers });
    check(list, { 'Contract list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/contracts/stats/status`, { headers });
    check(stats, { 'Contract stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/contracts/meta`, { headers });
    check(meta, { 'Contract meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Sla Profile', () => {
    const list = http.get(`${BASE}/api/v1/sla-profiles?size=20`, { headers });
    check(list, { 'SlaProfile list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/sla-profiles/stats/status`, { headers });
    check(stats, { 'SlaProfile stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/sla-profiles/meta`, { headers });
    check(meta, { 'SlaProfile meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
