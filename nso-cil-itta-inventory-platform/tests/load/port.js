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
  group('Port', () => {
    const list = http.get(`${BASE}/api/v1/ports?size=20`, { headers });
    check(list, { 'Port list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/ports/stats/status`, { headers });
    check(stats, { 'Port stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/ports/meta`, { headers });
    check(meta, { 'Port meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Interface', () => {
    const list = http.get(`${BASE}/api/v1/interfaces?size=20`, { headers });
    check(list, { 'Interface list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/interfaces/stats/status`, { headers });
    check(stats, { 'Interface stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/interfaces/meta`, { headers });
    check(meta, { 'Interface meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Sub Interface', () => {
    const list = http.get(`${BASE}/api/v1/sub-interfaces?size=20`, { headers });
    check(list, { 'SubInterface list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/sub-interfaces/stats/status`, { headers });
    check(stats, { 'SubInterface stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/sub-interfaces/meta`, { headers });
    check(meta, { 'SubInterface meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Lag Group', () => {
    const list = http.get(`${BASE}/api/v1/lag-groups?size=20`, { headers });
    check(list, { 'LagGroup list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/lag-groups/stats/status`, { headers });
    check(stats, { 'LagGroup stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/lag-groups/meta`, { headers });
    check(meta, { 'LagGroup meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Transceiver', () => {
    const list = http.get(`${BASE}/api/v1/transceivers?size=20`, { headers });
    check(list, { 'Transceiver list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/transceivers/stats/status`, { headers });
    check(stats, { 'Transceiver stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/transceivers/meta`, { headers });
    check(meta, { 'Transceiver meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
