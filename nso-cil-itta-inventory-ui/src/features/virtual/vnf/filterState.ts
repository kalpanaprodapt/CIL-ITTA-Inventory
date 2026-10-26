import type { LifecycleStatus } from '@/shared/types';
import type { VnfFilter, VnfLifecycleStage, VnfCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toVnfQuery(filter: VnfFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.vnfName) query.vnfName = filter.vnfName;
  if (filter.vnfType) query.vnfType = filter.vnfType;
  if (filter.flavor) query.flavor = filter.flavor;
  if (filter.vcpuMin !== undefined) query.vcpuMin = filter.vcpuMin;
  if (filter.vcpuMax !== undefined) query.vcpuMax = filter.vcpuMax;
  if (filter.memoryGbMin !== undefined) query.memoryGbMin = filter.memoryGbMin;
  if (filter.memoryGbMax !== undefined) query.memoryGbMax = filter.memoryGbMax;
  if (filter.instanceCountMin !== undefined) query.instanceCountMin = filter.instanceCountMin;
  if (filter.instanceCountMax !== undefined) query.instanceCountMax = filter.instanceCountMax;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parseVnfFilter(params: URLSearchParams): VnfFilter {
  const filter: VnfFilter = {};
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
  const vnfName = params.get('vnfName');
  if (vnfName) filter.vnfName = vnfName;
  const vnfType = params.get('vnfType');
  if (vnfType) filter.vnfType = vnfType;
  const flavor = params.get('flavor');
  if (flavor) filter.flavor = flavor;
  const vcpuMin = params.get('vcpuMin');
  if (vcpuMin !== null && vcpuMin !== '') filter.vcpuMin = Number(vcpuMin);
  const vcpuMax = params.get('vcpuMax');
  if (vcpuMax !== null && vcpuMax !== '') filter.vcpuMax = Number(vcpuMax);
  const memoryGbMin = params.get('memoryGbMin');
  if (memoryGbMin !== null && memoryGbMin !== '') filter.memoryGbMin = Number(memoryGbMin);
  const memoryGbMax = params.get('memoryGbMax');
  if (memoryGbMax !== null && memoryGbMax !== '') filter.memoryGbMax = Number(memoryGbMax);
  const instanceCountMin = params.get('instanceCountMin');
  if (instanceCountMin !== null && instanceCountMin !== '') filter.instanceCountMin = Number(instanceCountMin);
  const instanceCountMax = params.get('instanceCountMax');
  if (instanceCountMax !== null && instanceCountMax !== '') filter.instanceCountMax = Number(instanceCountMax);
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as VnfLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as VnfCriticality[];
  return filter;
}

export function serializeVnfFilter(filter: VnfFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.vnfName) params.set('vnfName', filter.vnfName);
  if (filter.vnfType) params.set('vnfType', filter.vnfType);
  if (filter.flavor) params.set('flavor', filter.flavor);
  if (filter.vcpuMin !== undefined) params.set('vcpuMin', String(filter.vcpuMin));
  if (filter.vcpuMax !== undefined) params.set('vcpuMax', String(filter.vcpuMax));
  if (filter.memoryGbMin !== undefined) params.set('memoryGbMin', String(filter.memoryGbMin));
  if (filter.memoryGbMax !== undefined) params.set('memoryGbMax', String(filter.memoryGbMax));
  if (filter.instanceCountMin !== undefined) params.set('instanceCountMin', String(filter.instanceCountMin));
  if (filter.instanceCountMax !== undefined) params.set('instanceCountMax', String(filter.instanceCountMax));
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countVnfActiveFilters(filter: VnfFilter): number {
  return Array.from(serializeVnfFilter(filter).keys()).length;
}
