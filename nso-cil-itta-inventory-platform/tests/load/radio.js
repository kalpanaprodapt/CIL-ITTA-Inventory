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
  group('Radio Unit', () => {
    const list = http.get(`${BASE}/api/v1/radio-units?size=20`, { headers });
    check(list, { 'RadioUnit list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/radio-units/stats/status`, { headers });
    check(stats, { 'RadioUnit stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/radio-units/meta`, { headers });
    check(meta, { 'RadioUnit meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Base Station', () => {
    const list = http.get(`${BASE}/api/v1/base-stations?size=20`, { headers });
    check(list, { 'BaseStation list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/base-stations/stats/status`, { headers });
    check(stats, { 'BaseStation stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/base-stations/meta`, { headers });
    check(meta, { 'BaseStation meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Antenna', () => {
    const list = http.get(`${BASE}/api/v1/antennas?size=20`, { headers });
    check(list, { 'Antenna list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/antennas/stats/status`, { headers });
    check(stats, { 'Antenna stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/antennas/meta`, { headers });
    check(meta, { 'Antenna meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Sector', () => {
    const list = http.get(`${BASE}/api/v1/sectors?size=20`, { headers });
    check(list, { 'Sector list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/sectors/stats/status`, { headers });
    check(stats, { 'Sector stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/sectors/meta`, { headers });
    check(meta, { 'Sector meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Backhaul Link', () => {
    const list = http.get(`${BASE}/api/v1/backhaul-links?size=20`, { headers });
    check(list, { 'BackhaulLink list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/backhaul-links/stats/status`, { headers });
    check(stats, { 'BackhaulLink stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/backhaul-links/meta`, { headers });
    check(meta, { 'BackhaulLink meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
