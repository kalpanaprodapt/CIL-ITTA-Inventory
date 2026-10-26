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
  group('Discovery Job', () => {
    const list = http.get(`${BASE}/api/v1/discovery-jobs?size=20`, { headers });
    check(list, { 'DiscoveryJob list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/discovery-jobs/stats/status`, { headers });
    check(stats, { 'DiscoveryJob stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/discovery-jobs/meta`, { headers });
    check(meta, { 'DiscoveryJob meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Discovery Profile', () => {
    const list = http.get(`${BASE}/api/v1/discovery-profiles?size=20`, { headers });
    check(list, { 'DiscoveryProfile list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/discovery-profiles/stats/status`, { headers });
    check(stats, { 'DiscoveryProfile stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/discovery-profiles/meta`, { headers });
    check(meta, { 'DiscoveryProfile meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Discovered Asset', () => {
    const list = http.get(`${BASE}/api/v1/discovered-assets?size=20`, { headers });
    check(list, { 'DiscoveredAsset list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/discovered-assets/stats/status`, { headers });
    check(stats, { 'DiscoveredAsset stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/discovered-assets/meta`, { headers });
    check(meta, { 'DiscoveredAsset meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Reconciliation Rule', () => {
    const list = http.get(`${BASE}/api/v1/reconciliation-rules?size=20`, { headers });
    check(list, { 'ReconciliationRule list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/reconciliation-rules/stats/status`, { headers });
    check(stats, { 'ReconciliationRule stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/reconciliation-rules/meta`, { headers });
    check(meta, { 'ReconciliationRule meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Reconciliation Result', () => {
    const list = http.get(`${BASE}/api/v1/reconciliation-results?size=20`, { headers });
    check(list, { 'ReconciliationResult list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/reconciliation-results/stats/status`, { headers });
    check(stats, { 'ReconciliationResult stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/reconciliation-results/meta`, { headers });
    check(meta, { 'ReconciliationResult meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
