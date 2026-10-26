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
  group('Country', () => {
    const list = http.get(`${BASE}/api/v1/countries?size=20`, { headers });
    check(list, { 'Country list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/countries/stats/status`, { headers });
    check(stats, { 'Country stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/countries/meta`, { headers });
    check(meta, { 'Country meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Region', () => {
    const list = http.get(`${BASE}/api/v1/regions?size=20`, { headers });
    check(list, { 'Region list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/regions/stats/status`, { headers });
    check(stats, { 'Region stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/regions/meta`, { headers });
    check(meta, { 'Region meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('City', () => {
    const list = http.get(`${BASE}/api/v1/cities?size=20`, { headers });
    check(list, { 'City list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/cities/stats/status`, { headers });
    check(stats, { 'City stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/cities/meta`, { headers });
    check(meta, { 'City meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Site', () => {
    const list = http.get(`${BASE}/api/v1/sites?size=20`, { headers });
    check(list, { 'Site list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/sites/stats/status`, { headers });
    check(stats, { 'Site stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/sites/meta`, { headers });
    check(meta, { 'Site meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Building', () => {
    const list = http.get(`${BASE}/api/v1/buildings?size=20`, { headers });
    check(list, { 'Building list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/buildings/stats/status`, { headers });
    check(stats, { 'Building stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/buildings/meta`, { headers });
    check(meta, { 'Building meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
