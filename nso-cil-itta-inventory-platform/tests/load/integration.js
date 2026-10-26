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
  group('Integration Endpoint', () => {
    const list = http.get(`${BASE}/api/v1/integration-endpoints?size=20`, { headers });
    check(list, { 'IntegrationEndpoint list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/integration-endpoints/stats/status`, { headers });
    check(stats, { 'IntegrationEndpoint stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/integration-endpoints/meta`, { headers });
    check(meta, { 'IntegrationEndpoint meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Sync Job', () => {
    const list = http.get(`${BASE}/api/v1/sync-jobs?size=20`, { headers });
    check(list, { 'SyncJob list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/sync-jobs/stats/status`, { headers });
    check(stats, { 'SyncJob stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/sync-jobs/meta`, { headers });
    check(meta, { 'SyncJob meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Sync Mapping', () => {
    const list = http.get(`${BASE}/api/v1/sync-mappings?size=20`, { headers });
    check(list, { 'SyncMapping list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/sync-mappings/stats/status`, { headers });
    check(stats, { 'SyncMapping stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/sync-mappings/meta`, { headers });
    check(meta, { 'SyncMapping meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Import Batch', () => {
    const list = http.get(`${BASE}/api/v1/import-batchs?size=20`, { headers });
    check(list, { 'ImportBatch list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/import-batchs/stats/status`, { headers });
    check(stats, { 'ImportBatch stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/import-batchs/meta`, { headers });
    check(meta, { 'ImportBatch meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Webhook Subscription', () => {
    const list = http.get(`${BASE}/api/v1/webhook-subscriptions?size=20`, { headers });
    check(list, { 'WebhookSubscription list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/webhook-subscriptions/stats/status`, { headers });
    check(stats, { 'WebhookSubscription stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/webhook-subscriptions/meta`, { headers });
    check(meta, { 'WebhookSubscription meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
