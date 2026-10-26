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
  group('Vlan', () => {
    const list = http.get(`${BASE}/api/v1/vlans?size=20`, { headers });
    check(list, { 'Vlan list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/vlans/stats/status`, { headers });
    check(stats, { 'Vlan stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/vlans/meta`, { headers });
    check(meta, { 'Vlan meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Vlan Pool', () => {
    const list = http.get(`${BASE}/api/v1/vlan-pools?size=20`, { headers });
    check(list, { 'VlanPool list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/vlan-pools/stats/status`, { headers });
    check(stats, { 'VlanPool stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/vlan-pools/meta`, { headers });
    check(meta, { 'VlanPool meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Route Target', () => {
    const list = http.get(`${BASE}/api/v1/route-targets?size=20`, { headers });
    check(list, { 'RouteTarget list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/route-targets/stats/status`, { headers });
    check(stats, { 'RouteTarget stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/route-targets/meta`, { headers });
    check(meta, { 'RouteTarget meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Bgp Session', () => {
    const list = http.get(`${BASE}/api/v1/bgp-sessions?size=20`, { headers });
    check(list, { 'BgpSession list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/bgp-sessions/stats/status`, { headers });
    check(stats, { 'BgpSession stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/bgp-sessions/meta`, { headers });
    check(meta, { 'BgpSession meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Ospf Area', () => {
    const list = http.get(`${BASE}/api/v1/ospf-areas?size=20`, { headers });
    check(list, { 'OspfArea list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/ospf-areas/stats/status`, { headers });
    check(stats, { 'OspfArea stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/ospf-areas/meta`, { headers });
    check(meta, { 'OspfArea meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
