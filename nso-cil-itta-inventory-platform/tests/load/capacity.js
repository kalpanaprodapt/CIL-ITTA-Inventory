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
  group('Capacity Plan', () => {
    const list = http.get(`${BASE}/api/v1/capacity-plans?size=20`, { headers });
    check(list, { 'CapacityPlan list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/capacity-plans/stats/status`, { headers });
    check(stats, { 'CapacityPlan stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/capacity-plans/meta`, { headers });
    check(meta, { 'CapacityPlan meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Utilization Sample', () => {
    const list = http.get(`${BASE}/api/v1/utilization-samples?size=20`, { headers });
    check(list, { 'UtilizationSample list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/utilization-samples/stats/status`, { headers });
    check(stats, { 'UtilizationSample stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/utilization-samples/meta`, { headers });
    check(meta, { 'UtilizationSample meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Threshold Policy', () => {
    const list = http.get(`${BASE}/api/v1/threshold-policies?size=20`, { headers });
    check(list, { 'ThresholdPolicy list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/threshold-policies/stats/status`, { headers });
    check(stats, { 'ThresholdPolicy stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/threshold-policies/meta`, { headers });
    check(meta, { 'ThresholdPolicy meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Forecast Model', () => {
    const list = http.get(`${BASE}/api/v1/forecast-models?size=20`, { headers });
    check(list, { 'ForecastModel list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/forecast-models/stats/status`, { headers });
    check(stats, { 'ForecastModel stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/forecast-models/meta`, { headers });
    check(meta, { 'ForecastModel meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Reservation', () => {
    const list = http.get(`${BASE}/api/v1/reservations?size=20`, { headers });
    check(list, { 'Reservation list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/reservations/stats/status`, { headers });
    check(stats, { 'Reservation stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/reservations/meta`, { headers });
    check(meta, { 'Reservation meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
