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
  group('Service Catalog Item', () => {
    const list = http.get(`${BASE}/api/v1/service-catalog-items?size=20`, { headers });
    check(list, { 'ServiceCatalogItem list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/service-catalog-items/stats/status`, { headers });
    check(stats, { 'ServiceCatalogItem stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/service-catalog-items/meta`, { headers });
    check(meta, { 'ServiceCatalogItem meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Service Instance', () => {
    const list = http.get(`${BASE}/api/v1/service-instances?size=20`, { headers });
    check(list, { 'ServiceInstance list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/service-instances/stats/status`, { headers });
    check(stats, { 'ServiceInstance stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/service-instances/meta`, { headers });
    check(meta, { 'ServiceInstance meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Service Order', () => {
    const list = http.get(`${BASE}/api/v1/service-orders?size=20`, { headers });
    check(list, { 'ServiceOrder list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/service-orders/stats/status`, { headers });
    check(stats, { 'ServiceOrder stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/service-orders/meta`, { headers });
    check(meta, { 'ServiceOrder meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Service Dependency', () => {
    const list = http.get(`${BASE}/api/v1/service-dependencies?size=20`, { headers });
    check(list, { 'ServiceDependency list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/service-dependencies/stats/status`, { headers });
    check(stats, { 'ServiceDependency stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/service-dependencies/meta`, { headers });
    check(meta, { 'ServiceDependency meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Service Endpoint', () => {
    const list = http.get(`${BASE}/api/v1/service-endpoints?size=20`, { headers });
    check(list, { 'ServiceEndpoint list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/service-endpoints/stats/status`, { headers });
    check(stats, { 'ServiceEndpoint stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/service-endpoints/meta`, { headers });
    check(meta, { 'ServiceEndpoint meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
