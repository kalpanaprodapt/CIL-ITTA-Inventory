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
  group('Cable', () => {
    const list = http.get(`${BASE}/api/v1/cables?size=20`, { headers });
    check(list, { 'Cable list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/cables/stats/status`, { headers });
    check(stats, { 'Cable stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/cables/meta`, { headers });
    check(meta, { 'Cable meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Patch Panel', () => {
    const list = http.get(`${BASE}/api/v1/patch-panels?size=20`, { headers });
    check(list, { 'PatchPanel list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/patch-panels/stats/status`, { headers });
    check(stats, { 'PatchPanel stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/patch-panels/meta`, { headers });
    check(meta, { 'PatchPanel meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Patch Port', () => {
    const list = http.get(`${BASE}/api/v1/patch-ports?size=20`, { headers });
    check(list, { 'PatchPort list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/patch-ports/stats/status`, { headers });
    check(stats, { 'PatchPort stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/patch-ports/meta`, { headers });
    check(meta, { 'PatchPort meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Splice', () => {
    const list = http.get(`${BASE}/api/v1/splices?size=20`, { headers });
    check(list, { 'Splice list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/splices/stats/status`, { headers });
    check(stats, { 'Splice stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/splices/meta`, { headers });
    check(meta, { 'Splice meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Fiber Strand', () => {
    const list = http.get(`${BASE}/api/v1/fiber-strands?size=20`, { headers });
    check(list, { 'FiberStrand list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/fiber-strands/stats/status`, { headers });
    check(stats, { 'FiberStrand stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/fiber-strands/meta`, { headers });
    check(meta, { 'FiberStrand meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
