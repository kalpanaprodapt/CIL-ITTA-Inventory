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
  group('Floor', () => {
    const list = http.get(`${BASE}/api/v1/floors?size=20`, { headers });
    check(list, { 'Floor list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/floors/stats/status`, { headers });
    check(stats, { 'Floor stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/floors/meta`, { headers });
    check(meta, { 'Floor meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Room', () => {
    const list = http.get(`${BASE}/api/v1/rooms?size=20`, { headers });
    check(list, { 'Room list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/rooms/stats/status`, { headers });
    check(stats, { 'Room stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/rooms/meta`, { headers });
    check(meta, { 'Room meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Rack', () => {
    const list = http.get(`${BASE}/api/v1/racks?size=20`, { headers });
    check(list, { 'Rack list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/racks/stats/status`, { headers });
    check(stats, { 'Rack stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/racks/meta`, { headers });
    check(meta, { 'Rack meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Rack Unit', () => {
    const list = http.get(`${BASE}/api/v1/rack-units?size=20`, { headers });
    check(list, { 'RackUnit list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/rack-units/stats/status`, { headers });
    check(stats, { 'RackUnit stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/rack-units/meta`, { headers });
    check(meta, { 'RackUnit meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Power Feed', () => {
    const list = http.get(`${BASE}/api/v1/power-feeds?size=20`, { headers });
    check(list, { 'PowerFeed list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/power-feeds/stats/status`, { headers });
    check(stats, { 'PowerFeed stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/power-feeds/meta`, { headers });
    check(meta, { 'PowerFeed meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
