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
  group('Link', () => {
    const list = http.get(`${BASE}/api/v1/links?size=20`, { headers });
    check(list, { 'Link list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/links/stats/status`, { headers });
    check(stats, { 'Link stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/links/meta`, { headers });
    check(meta, { 'Link meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Topology Node', () => {
    const list = http.get(`${BASE}/api/v1/topology-nodes?size=20`, { headers });
    check(list, { 'TopologyNode list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/topology-nodes/stats/status`, { headers });
    check(stats, { 'TopologyNode stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/topology-nodes/meta`, { headers });
    check(meta, { 'TopologyNode meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Topology Edge', () => {
    const list = http.get(`${BASE}/api/v1/topology-edges?size=20`, { headers });
    check(list, { 'TopologyEdge list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/topology-edges/stats/status`, { headers });
    check(stats, { 'TopologyEdge stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/topology-edges/meta`, { headers });
    check(meta, { 'TopologyEdge meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Topology View', () => {
    const list = http.get(`${BASE}/api/v1/topology-views?size=20`, { headers });
    check(list, { 'TopologyView list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/topology-views/stats/status`, { headers });
    check(stats, { 'TopologyView stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/topology-views/meta`, { headers });
    check(meta, { 'TopologyView meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Path Trace', () => {
    const list = http.get(`${BASE}/api/v1/path-traces?size=20`, { headers });
    check(list, { 'PathTrace list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/path-traces/stats/status`, { headers });
    check(stats, { 'PathTrace stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/path-traces/meta`, { headers });
    check(meta, { 'PathTrace meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
