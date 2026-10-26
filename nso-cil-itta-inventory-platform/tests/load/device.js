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
  group('Network Device', () => {
    const list = http.get(`${BASE}/api/v1/network-devices?size=20`, { headers });
    check(list, { 'NetworkDevice list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/network-devices/stats/status`, { headers });
    check(stats, { 'NetworkDevice stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/network-devices/meta`, { headers });
    check(meta, { 'NetworkDevice meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Chassis', () => {
    const list = http.get(`${BASE}/api/v1/chassises?size=20`, { headers });
    check(list, { 'Chassis list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/chassises/stats/status`, { headers });
    check(stats, { 'Chassis stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/chassises/meta`, { headers });
    check(meta, { 'Chassis meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Line Card', () => {
    const list = http.get(`${BASE}/api/v1/line-cards?size=20`, { headers });
    check(list, { 'LineCard list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/line-cards/stats/status`, { headers });
    check(stats, { 'LineCard stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/line-cards/meta`, { headers });
    check(meta, { 'LineCard meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Power Supply', () => {
    const list = http.get(`${BASE}/api/v1/power-supplies?size=20`, { headers });
    check(list, { 'PowerSupply list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/power-supplies/stats/status`, { headers });
    check(stats, { 'PowerSupply stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/power-supplies/meta`, { headers });
    check(meta, { 'PowerSupply meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Fan Tray', () => {
    const list = http.get(`${BASE}/api/v1/fan-traies?size=20`, { headers });
    check(list, { 'FanTray list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/fan-traies/stats/status`, { headers });
    check(stats, { 'FanTray stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/fan-traies/meta`, { headers });
    check(meta, { 'FanTray meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
