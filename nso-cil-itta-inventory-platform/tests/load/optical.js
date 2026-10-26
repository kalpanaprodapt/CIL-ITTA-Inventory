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
  group('Optical Channel', () => {
    const list = http.get(`${BASE}/api/v1/optical-channels?size=20`, { headers });
    check(list, { 'OpticalChannel list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/optical-channels/stats/status`, { headers });
    check(stats, { 'OpticalChannel stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/optical-channels/meta`, { headers });
    check(meta, { 'OpticalChannel meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Wdm System', () => {
    const list = http.get(`${BASE}/api/v1/wdm-systems?size=20`, { headers });
    check(list, { 'WdmSystem list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/wdm-systems/stats/status`, { headers });
    check(stats, { 'WdmSystem stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/wdm-systems/meta`, { headers });
    check(meta, { 'WdmSystem meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Otn Link', () => {
    const list = http.get(`${BASE}/api/v1/otn-links?size=20`, { headers });
    check(list, { 'OtnLink list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/otn-links/stats/status`, { headers });
    check(stats, { 'OtnLink stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/otn-links/meta`, { headers });
    check(meta, { 'OtnLink meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Amplifier', () => {
    const list = http.get(`${BASE}/api/v1/amplifiers?size=20`, { headers });
    check(list, { 'Amplifier list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/amplifiers/stats/status`, { headers });
    check(stats, { 'Amplifier stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/amplifiers/meta`, { headers });
    check(meta, { 'Amplifier meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Roadm', () => {
    const list = http.get(`${BASE}/api/v1/roadms?size=20`, { headers });
    check(list, { 'Roadm list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/roadms/stats/status`, { headers });
    check(stats, { 'Roadm stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/roadms/meta`, { headers });
    check(meta, { 'Roadm meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
