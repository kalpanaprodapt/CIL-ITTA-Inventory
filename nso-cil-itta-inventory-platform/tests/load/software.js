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
  group('Software Image', () => {
    const list = http.get(`${BASE}/api/v1/software-images?size=20`, { headers });
    check(list, { 'SoftwareImage list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/software-images/stats/status`, { headers });
    check(stats, { 'SoftwareImage stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/software-images/meta`, { headers });
    check(meta, { 'SoftwareImage meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Software Version', () => {
    const list = http.get(`${BASE}/api/v1/software-versions?size=20`, { headers });
    check(list, { 'SoftwareVersion list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/software-versions/stats/status`, { headers });
    check(stats, { 'SoftwareVersion stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/software-versions/meta`, { headers });
    check(meta, { 'SoftwareVersion meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Patch', () => {
    const list = http.get(`${BASE}/api/v1/patchs?size=20`, { headers });
    check(list, { 'Patch list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/patchs/stats/status`, { headers });
    check(stats, { 'Patch stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/patchs/meta`, { headers });
    check(meta, { 'Patch meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('License Key', () => {
    const list = http.get(`${BASE}/api/v1/license-keies?size=20`, { headers });
    check(list, { 'LicenseKey list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/license-keies/stats/status`, { headers });
    check(stats, { 'LicenseKey stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/license-keies/meta`, { headers });
    check(meta, { 'LicenseKey meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('License Pool', () => {
    const list = http.get(`${BASE}/api/v1/license-pools?size=20`, { headers });
    check(list, { 'LicensePool list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/license-pools/stats/status`, { headers });
    check(stats, { 'LicensePool stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/license-pools/meta`, { headers });
    check(meta, { 'LicensePool meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
