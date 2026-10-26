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
  group('Vnf', () => {
    const list = http.get(`${BASE}/api/v1/vnfs?size=20`, { headers });
    check(list, { 'Vnf list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/vnfs/stats/status`, { headers });
    check(stats, { 'Vnf stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/vnfs/meta`, { headers });
    check(meta, { 'Vnf meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Hypervisor', () => {
    const list = http.get(`${BASE}/api/v1/hypervisors?size=20`, { headers });
    check(list, { 'Hypervisor list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/hypervisors/stats/status`, { headers });
    check(stats, { 'Hypervisor stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/hypervisors/meta`, { headers });
    check(meta, { 'Hypervisor meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Virtual Machine', () => {
    const list = http.get(`${BASE}/api/v1/virtual-machines?size=20`, { headers });
    check(list, { 'VirtualMachine list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/virtual-machines/stats/status`, { headers });
    check(stats, { 'VirtualMachine stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/virtual-machines/meta`, { headers });
    check(meta, { 'VirtualMachine meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('Cloud Account', () => {
    const list = http.get(`${BASE}/api/v1/cloud-accounts?size=20`, { headers });
    check(list, { 'CloudAccount list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/cloud-accounts/stats/status`, { headers });
    check(stats, { 'CloudAccount stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/cloud-accounts/meta`, { headers });
    check(meta, { 'CloudAccount meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });

  group('K8s Cluster', () => {
    const list = http.get(`${BASE}/api/v1/k8s-clusters?size=20`, { headers });
    check(list, { 'K8sCluster list 200': (r) => r.status === 200 });
    const stats = http.get(`${BASE}/api/v1/k8s-clusters/stats/status`, { headers });
    check(stats, { 'K8sCluster stats 200': (r) => r.status === 200 });
    const meta = http.get(`${BASE}/api/v1/k8s-clusters/meta`, { headers });
    check(meta, { 'K8sCluster meta 200': (r) => r.status === 200 });
    sleep(0.2);
  });
}
