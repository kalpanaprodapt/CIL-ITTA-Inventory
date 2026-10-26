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
  group('Change Request', () => {
    const list = http.get(`${BASE}/api/v1/change-requests?size=20`, { headers });
    check(list, { 'ChangeRequest list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/change-requests/stats/status`, { headers });
    check(stats, { 'ChangeRequest stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/change-requests/meta`, { headers });
    check(meta, { 'ChangeRequest meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Maintenance Window', () => {
    const list = http.get(`${BASE}/api/v1/maintenance-windows?size=20`, { headers });
    check(list, { 'MaintenanceWindow list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/maintenance-windows/stats/status`, { headers });
    check(stats, { 'MaintenanceWindow stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/maintenance-windows/meta`, { headers });
    check(meta, { 'MaintenanceWindow meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Work Order', () => {
    const list = http.get(`${BASE}/api/v1/work-orders?size=20`, { headers });
    check(list, { 'WorkOrder list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/work-orders/stats/status`, { headers });
    check(stats, { 'WorkOrder stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/work-orders/meta`, { headers });
    check(meta, { 'WorkOrder meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Field Task', () => {
    const list = http.get(`${BASE}/api/v1/field-tasks?size=20`, { headers });
    check(list, { 'FieldTask list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/field-tasks/stats/status`, { headers });
    check(stats, { 'FieldTask stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/field-tasks/meta`, { headers });
    check(meta, { 'FieldTask meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Technician', () => {
    const list = http.get(`${BASE}/api/v1/technicians?size=20`, { headers });
    check(list, { 'Technician list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/technicians/stats/status`, { headers });
    check(stats, { 'Technician stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/technicians/meta`, { headers });
    check(meta, { 'Technician meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
