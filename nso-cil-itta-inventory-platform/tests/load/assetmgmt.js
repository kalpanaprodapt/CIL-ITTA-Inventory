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
  group('Asset', () => {
    const list = http.get(`${BASE}/api/v1/assets?size=20`, { headers });
    check(list, { 'Asset list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/assets/stats/status`, { headers });
    check(stats, { 'Asset stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/assets/meta`, { headers });
    check(meta, { 'Asset meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Asset Tag', () => {
    const list = http.get(`${BASE}/api/v1/asset-tags?size=20`, { headers });
    check(list, { 'AssetTag list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/asset-tags/stats/status`, { headers });
    check(stats, { 'AssetTag stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/asset-tags/meta`, { headers });
    check(meta, { 'AssetTag meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Purchase Order', () => {
    const list = http.get(`${BASE}/api/v1/purchase-orders?size=20`, { headers });
    check(list, { 'PurchaseOrder list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/purchase-orders/stats/status`, { headers });
    check(stats, { 'PurchaseOrder stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/purchase-orders/meta`, { headers });
    check(meta, { 'PurchaseOrder meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Invoice', () => {
    const list = http.get(`${BASE}/api/v1/invoices?size=20`, { headers });
    check(list, { 'Invoice list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/invoices/stats/status`, { headers });
    check(stats, { 'Invoice stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/invoices/meta`, { headers });
    check(meta, { 'Invoice meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Depreciation Schedule', () => {
    const list = http.get(`${BASE}/api/v1/depreciation-schedules?size=20`, { headers });
    check(list, { 'DepreciationSchedule list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/depreciation-schedules/stats/status`, { headers });
    check(stats, { 'DepreciationSchedule stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/depreciation-schedules/meta`, { headers });
    check(meta, { 'DepreciationSchedule meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
