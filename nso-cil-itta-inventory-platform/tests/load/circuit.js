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
  group('Circuit', () => {
    const list = http.get(`${BASE}/api/v1/circuits?size=20`, { headers });
    check(list, { 'Circuit list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/circuits/stats/status`, { headers });
    check(stats, { 'Circuit stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/circuits/meta`, { headers });
    check(meta, { 'Circuit meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Circuit Segment', () => {
    const list = http.get(`${BASE}/api/v1/circuit-segments?size=20`, { headers });
    check(list, { 'CircuitSegment list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/circuit-segments/stats/status`, { headers });
    check(stats, { 'CircuitSegment stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/circuit-segments/meta`, { headers });
    check(meta, { 'CircuitSegment meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Circuit Provider', () => {
    const list = http.get(`${BASE}/api/v1/circuit-providers?size=20`, { headers });
    check(list, { 'CircuitProvider list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/circuit-providers/stats/status`, { headers });
    check(stats, { 'CircuitProvider stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/circuit-providers/meta`, { headers });
    check(meta, { 'CircuitProvider meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Circuit Termination', () => {
    const list = http.get(`${BASE}/api/v1/circuit-terminations?size=20`, { headers });
    check(list, { 'CircuitTermination list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/circuit-terminations/stats/status`, { headers });
    check(stats, { 'CircuitTermination stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/circuit-terminations/meta`, { headers });
    check(meta, { 'CircuitTermination meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Circuit Order', () => {
    const list = http.get(`${BASE}/api/v1/circuit-orders?size=20`, { headers });
    check(list, { 'CircuitOrder list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/circuit-orders/stats/status`, { headers });
    check(stats, { 'CircuitOrder stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/circuit-orders/meta`, { headers });
    check(meta, { 'CircuitOrder meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
