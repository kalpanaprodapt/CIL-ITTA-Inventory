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
  group('Alarm Definition', () => {
    const list = http.get(`${BASE}/api/v1/alarm-definitions?size=20`, { headers });
    check(list, { 'AlarmDefinition list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/alarm-definitions/stats/status`, { headers });
    check(stats, { 'AlarmDefinition stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/alarm-definitions/meta`, { headers });
    check(meta, { 'AlarmDefinition meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Alarm Instance', () => {
    const list = http.get(`${BASE}/api/v1/alarm-instances?size=20`, { headers });
    check(list, { 'AlarmInstance list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/alarm-instances/stats/status`, { headers });
    check(stats, { 'AlarmInstance stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/alarm-instances/meta`, { headers });
    check(meta, { 'AlarmInstance meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Event Source', () => {
    const list = http.get(`${BASE}/api/v1/event-sources?size=20`, { headers });
    check(list, { 'EventSource list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/event-sources/stats/status`, { headers });
    check(stats, { 'EventSource stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/event-sources/meta`, { headers });
    check(meta, { 'EventSource meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Suppression Rule', () => {
    const list = http.get(`${BASE}/api/v1/suppression-rules?size=20`, { headers });
    check(list, { 'SuppressionRule list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/suppression-rules/stats/status`, { headers });
    check(stats, { 'SuppressionRule stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/suppression-rules/meta`, { headers });
    check(meta, { 'SuppressionRule meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Ticket Link', () => {
    const list = http.get(`${BASE}/api/v1/ticket-links?size=20`, { headers });
    check(list, { 'TicketLink list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/ticket-links/stats/status`, { headers });
    check(stats, { 'TicketLink stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/ticket-links/meta`, { headers });
    check(meta, { 'TicketLink meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
