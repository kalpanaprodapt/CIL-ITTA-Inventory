import type { LifecycleStatus } from '@/shared/types';
import type { VirtualMachineFilter, VirtualMachinePowerState, VirtualMachineCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toVirtualMachineQuery(filter: VirtualMachineFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.hypervisorId) query.hypervisorId = filter.hypervisorId;
  if (filter.vmName) query.vmName = filter.vmName;
  if (filter.vcpuMin !== undefined) query.vcpuMin = filter.vcpuMin;
  if (filter.vcpuMax !== undefined) query.vcpuMax = filter.vcpuMax;
  if (filter.memoryGbMin !== undefined) query.memoryGbMin = filter.memoryGbMin;
  if (filter.memoryGbMax !== undefined) query.memoryGbMax = filter.memoryGbMax;
  if (filter.diskGbMin !== undefined) query.diskGbMin = filter.diskGbMin;
  if (filter.diskGbMax !== undefined) query.diskGbMax = filter.diskGbMax;
  if (filter.powerState?.length) query.powerState = filter.powerState.join(',');
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parseVirtualMachineFilter(params: URLSearchParams): VirtualMachineFilter {
  const filter: VirtualMachineFilter = {};
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
  const hypervisorId = params.get('hypervisorId');
  if (hypervisorId) filter.hypervisorId = hypervisorId;
  const vmName = params.get('vmName');
  if (vmName) filter.vmName = vmName;
  const vcpuMin = params.get('vcpuMin');
  if (vcpuMin !== null && vcpuMin !== '') filter.vcpuMin = Number(vcpuMin);
  const vcpuMax = params.get('vcpuMax');
  if (vcpuMax !== null && vcpuMax !== '') filter.vcpuMax = Number(vcpuMax);
  const memoryGbMin = params.get('memoryGbMin');
  if (memoryGbMin !== null && memoryGbMin !== '') filter.memoryGbMin = Number(memoryGbMin);
  const memoryGbMax = params.get('memoryGbMax');
  if (memoryGbMax !== null && memoryGbMax !== '') filter.memoryGbMax = Number(memoryGbMax);
  const diskGbMin = params.get('diskGbMin');
  if (diskGbMin !== null && diskGbMin !== '') filter.diskGbMin = Number(diskGbMin);
  const diskGbMax = params.get('diskGbMax');
  if (diskGbMax !== null && diskGbMax !== '') filter.diskGbMax = Number(diskGbMax);
  const powerState = params.get('powerState');
  if (powerState) filter.powerState = powerState.split(',') as VirtualMachinePowerState[];
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as VirtualMachineCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  return filter;
}

export function serializeVirtualMachineFilter(filter: VirtualMachineFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.hypervisorId) params.set('hypervisorId', filter.hypervisorId);
  if (filter.vmName) params.set('vmName', filter.vmName);
  if (filter.vcpuMin !== undefined) params.set('vcpuMin', String(filter.vcpuMin));
  if (filter.vcpuMax !== undefined) params.set('vcpuMax', String(filter.vcpuMax));
  if (filter.memoryGbMin !== undefined) params.set('memoryGbMin', String(filter.memoryGbMin));
  if (filter.memoryGbMax !== undefined) params.set('memoryGbMax', String(filter.memoryGbMax));
  if (filter.diskGbMin !== undefined) params.set('diskGbMin', String(filter.diskGbMin));
  if (filter.diskGbMax !== undefined) params.set('diskGbMax', String(filter.diskGbMax));
  if (filter.powerState?.length) params.set('powerState', filter.powerState.join(','));
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countVirtualMachineActiveFilters(filter: VirtualMachineFilter): number {
  return Array.from(serializeVirtualMachineFilter(filter).keys()).length;
}
