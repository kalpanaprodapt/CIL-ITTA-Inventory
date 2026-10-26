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
  group('Ip Subnet', () => {
    const list = http.get(`${BASE}/api/v1/ip-subnets?size=20`, { headers });
    check(list, { 'IpSubnet list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/ip-subnets/stats/status`, { headers });
    check(stats, { 'IpSubnet stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/ip-subnets/meta`, { headers });
    check(meta, { 'IpSubnet meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Ip Address', () => {
    const list = http.get(`${BASE}/api/v1/ip-addresses?size=20`, { headers });
    check(list, { 'IpAddress list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/ip-addresses/stats/status`, { headers });
    check(stats, { 'IpAddress stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/ip-addresses/meta`, { headers });
    check(meta, { 'IpAddress meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Ip Pool', () => {
    const list = http.get(`${BASE}/api/v1/ip-pools?size=20`, { headers });
    check(list, { 'IpPool list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/ip-pools/stats/status`, { headers });
    check(stats, { 'IpPool stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/ip-pools/meta`, { headers });
    check(meta, { 'IpPool meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Vrf Instance', () => {
    const list = http.get(`${BASE}/api/v1/vrf-instances?size=20`, { headers });
    check(list, { 'VrfInstance list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/vrf-instances/stats/status`, { headers });
    check(stats, { 'VrfInstance stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/vrf-instances/meta`, { headers });
    check(meta, { 'VrfInstance meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Dns Record', () => {
    const list = http.get(`${BASE}/api/v1/dns-records?size=20`, { headers });
    check(list, { 'DnsRecord list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/dns-records/stats/status`, { headers });
    check(stats, { 'DnsRecord stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/dns-records/meta`, { headers });
    check(meta, { 'DnsRecord meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
