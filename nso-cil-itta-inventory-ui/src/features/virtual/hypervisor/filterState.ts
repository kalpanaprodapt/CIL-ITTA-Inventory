import type { LifecycleStatus } from '@/shared/types';
import type { HypervisorFilter, HypervisorHypervisorType, HypervisorCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toHypervisorQuery(filter: HypervisorFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.hostDeviceId) query.hostDeviceId = filter.hostDeviceId;
  if (filter.hypervisorType?.length) query.hypervisorType = filter.hypervisorType.join(',');
  if (filter.hvVersion) query.hvVersion = filter.hvVersion;
  if (filter.vcpuCapacityMin !== undefined) query.vcpuCapacityMin = filter.vcpuCapacityMin;
  if (filter.vcpuCapacityMax !== undefined) query.vcpuCapacityMax = filter.vcpuCapacityMax;
  if (filter.memoryGbCapacityMin !== undefined) query.memoryGbCapacityMin = filter.memoryGbCapacityMin;
  if (filter.memoryGbCapacityMax !== undefined) query.memoryGbCapacityMax = filter.memoryGbCapacityMax;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseHypervisorFilter(params: URLSearchParams): HypervisorFilter {
  const filter: HypervisorFilter = {};
  const q = params.get('q');
  if (q) filter.q = q;
  const status = params.get('status');
  if (status) filter.status = status.split(',') as LifecycleStatus[];
  const code = params.get('code');
  if (code) filter.code = code;
  const name = params.get('name');
  if (name) filter.name = name;
  const externalRef = params.get('externalRef');
  if (externalRef) filter.externalRef = externalRef;
  const hostDeviceId = params.get('hostDeviceId');
  if (hostDeviceId) filter.hostDeviceId = hostDeviceId;
  const hypervisorType = params.get('hypervisorType');
  if (hypervisorType) filter.hypervisorType = hypervisorType.split(',') as HypervisorHypervisorType[];
  const hvVersion = params.get('hvVersion');
  if (hvVersion) filter.hvVersion = hvVersion;
  const vcpuCapacityMin = params.get('vcpuCapacityMin');
  if (vcpuCapacityMin !== null && vcpuCapacityMin !== '') filter.vcpuCapacityMin = Number(vcpuCapacityMin);
  const vcpuCapacityMax = params.get('vcpuCapacityMax');
  if (vcpuCapacityMax !== null && vcpuCapacityMax !== '') filter.vcpuCapacityMax = Number(vcpuCapacityMax);
  const memoryGbCapacityMin = params.get('memoryGbCapacityMin');
  if (memoryGbCapacityMin !== null && memoryGbCapacityMin !== '') filter.memoryGbCapacityMin = Number(memoryGbCapacityMin);
  const memoryGbCapacityMax = params.get('memoryGbCapacityMax');
  if (memoryGbCapacityMax !== null && memoryGbCapacityMax !== '') filter.memoryGbCapacityMax = Number(memoryGbCapacityMax);
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as HypervisorCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeHypervisorFilter(filter: HypervisorFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.hostDeviceId) params.set('hostDeviceId', filter.hostDeviceId);
  if (filter.hypervisorType?.length) params.set('hypervisorType', filter.hypervisorType.join(','));
  if (filter.hvVersion) params.set('hvVersion', filter.hvVersion);
  if (filter.vcpuCapacityMin !== undefined) params.set('vcpuCapacityMin', String(filter.vcpuCapacityMin));
  if (filter.vcpuCapacityMax !== undefined) params.set('vcpuCapacityMax', String(filter.vcpuCapacityMax));
  if (filter.memoryGbCapacityMin !== undefined) params.set('memoryGbCapacityMin', String(filter.memoryGbCapacityMin));
  if (filter.memoryGbCapacityMax !== undefined) params.set('memoryGbCapacityMax', String(filter.memoryGbCapacityMax));
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countHypervisorActiveFilters(filter: HypervisorFilter): number {
  return Array.from(serializeHypervisorFilter(filter).keys()).length;
}
