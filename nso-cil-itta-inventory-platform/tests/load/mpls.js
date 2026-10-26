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
  group('Mpls Lsp', () => {
    const list = http.get(`${BASE}/api/v1/mpls-lsps?size=20`, { headers });
    check(list, { 'MplsLsp list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/mpls-lsps/stats/status`, { headers });
    check(stats, { 'MplsLsp stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/mpls-lsps/meta`, { headers });
    check(meta, { 'MplsLsp meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Vpn Service', () => {
    const list = http.get(`${BASE}/api/v1/vpn-services?size=20`, { headers });
    check(list, { 'VpnService list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/vpn-services/stats/status`, { headers });
    check(stats, { 'VpnService stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/vpn-services/meta`, { headers });
    check(meta, { 'VpnService meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Pseudo Wire', () => {
    const list = http.get(`${BASE}/api/v1/pseudo-wires?size=20`, { headers });
    check(list, { 'PseudoWire list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/pseudo-wires/stats/status`, { headers });
    check(stats, { 'PseudoWire stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/pseudo-wires/meta`, { headers });
    check(meta, { 'PseudoWire meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Tunnel Endpoint', () => {
    const list = http.get(`${BASE}/api/v1/tunnel-endpoints?size=20`, { headers });
    check(list, { 'TunnelEndpoint list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/tunnel-endpoints/stats/status`, { headers });
    check(stats, { 'TunnelEndpoint stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/tunnel-endpoints/meta`, { headers });
    check(meta, { 'TunnelEndpoint meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Qos Policy', () => {
    const list = http.get(`${BASE}/api/v1/qos-policies?size=20`, { headers });
    check(list, { 'QosPolicy list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/qos-policies/stats/status`, { headers });
    check(stats, { 'QosPolicy stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/qos-policies/meta`, { headers });
    check(meta, { 'QosPolicy meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
