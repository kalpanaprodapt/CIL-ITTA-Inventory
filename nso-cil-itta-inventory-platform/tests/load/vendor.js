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
  group('Vendor', () => {
    const list = http.get(`${BASE}/api/v1/vendors?size=20`, { headers });
    check(list, { 'Vendor list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/vendors/stats/status`, { headers });
    check(stats, { 'Vendor stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/vendors/meta`, { headers });
    check(meta, { 'Vendor meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Hardware Model', () => {
    const list = http.get(`${BASE}/api/v1/hardware-models?size=20`, { headers });
    check(list, { 'HardwareModel list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/hardware-models/stats/status`, { headers });
    check(stats, { 'HardwareModel stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/hardware-models/meta`, { headers });
    check(meta, { 'HardwareModel meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Sku', () => {
    const list = http.get(`${BASE}/api/v1/skus?size=20`, { headers });
    check(list, { 'Sku list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/skus/stats/status`, { headers });
    check(stats, { 'Sku stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/skus/meta`, { headers });
    check(meta, { 'Sku meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Support Contract', () => {
    const list = http.get(`${BASE}/api/v1/support-contracts?size=20`, { headers });
    check(list, { 'SupportContract list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/support-contracts/stats/status`, { headers });
    check(stats, { 'SupportContract stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/support-contracts/meta`, { headers });
    check(meta, { 'SupportContract meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Warranty', () => {
    const list = http.get(`${BASE}/api/v1/warranties?size=20`, { headers });
    check(list, { 'Warranty list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/warranties/stats/status`, { headers });
    check(stats, { 'Warranty stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/warranties/meta`, { headers });
    check(meta, { 'Warranty meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
