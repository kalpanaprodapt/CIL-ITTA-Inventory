import type { LifecycleStatus } from '@/shared/types';
import type { IpAddressFilter, IpAddressAssignmentType } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toIpAddressQuery(filter: IpAddressFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.subnetId) query.subnetId = filter.subnetId;
  if (filter.address) query.address = filter.address;
  if (filter.assignedToId) query.assignedToId = filter.assignedToId;
  if (filter.assignmentType?.length) query.assignmentType = filter.assignmentType.join(',');
  if (filter.dnsName) query.dnsName = filter.dnsName;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseIpAddressFilter(params: URLSearchParams): IpAddressFilter {
  const filter: IpAddressFilter = {};
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
  const subnetId = params.get('subnetId');
  if (subnetId) filter.subnetId = subnetId;
  const address = params.get('address');
  if (address) filter.address = address;
  const assignedToId = params.get('assignedToId');
  if (assignedToId) filter.assignedToId = assignedToId;
  const assignmentType = params.get('assignmentType');
  if (assignmentType) filter.assignmentType = assignmentType.split(',') as IpAddressAssignmentType[];
  const dnsName = params.get('dnsName');
  if (dnsName) filter.dnsName = dnsName;
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
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  return filter;
}

export function serializeIpAddressFilter(filter: IpAddressFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.subnetId) params.set('subnetId', filter.subnetId);
  if (filter.address) params.set('address', filter.address);
  if (filter.assignedToId) params.set('assignedToId', filter.assignedToId);
  if (filter.assignmentType?.length) params.set('assignmentType', filter.assignmentType.join(','));
  if (filter.dnsName) params.set('dnsName', filter.dnsName);
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countIpAddressActiveFilters(filter: IpAddressFilter): number {
  return Array.from(serializeIpAddressFilter(filter).keys()).length;
}
