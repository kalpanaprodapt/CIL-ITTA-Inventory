import type { LifecycleStatus } from '@/shared/types';
import type { SubInterfaceFilter } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSubInterfaceQuery(filter: SubInterfaceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.interfaceId) query.interfaceId = filter.interfaceId;
  if (filter.vlanTagMin !== undefined) query.vlanTagMin = filter.vlanTagMin;
  if (filter.vlanTagMax !== undefined) query.vlanTagMax = filter.vlanTagMax;
  if (filter.encapsulation) query.encapsulation = filter.encapsulation;
  if (filter.ipv4Address) query.ipv4Address = filter.ipv4Address;
  if (filter.ipv6Address) query.ipv6Address = filter.ipv6Address;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseSubInterfaceFilter(params: URLSearchParams): SubInterfaceFilter {
  const filter: SubInterfaceFilter = {};
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
  const interfaceId = params.get('interfaceId');
  if (interfaceId) filter.interfaceId = interfaceId;
  const vlanTagMin = params.get('vlanTagMin');
  if (vlanTagMin !== null && vlanTagMin !== '') filter.vlanTagMin = Number(vlanTagMin);
  const vlanTagMax = params.get('vlanTagMax');
  if (vlanTagMax !== null && vlanTagMax !== '') filter.vlanTagMax = Number(vlanTagMax);
  const encapsulation = params.get('encapsulation');
  if (encapsulation) filter.encapsulation = encapsulation;
  const ipv4Address = params.get('ipv4Address');
  if (ipv4Address) filter.ipv4Address = ipv4Address;
  const ipv6Address = params.get('ipv6Address');
  if (ipv6Address) filter.ipv6Address = ipv6Address;
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

export function serializeSubInterfaceFilter(filter: SubInterfaceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.interfaceId) params.set('interfaceId', filter.interfaceId);
  if (filter.vlanTagMin !== undefined) params.set('vlanTagMin', String(filter.vlanTagMin));
  if (filter.vlanTagMax !== undefined) params.set('vlanTagMax', String(filter.vlanTagMax));
  if (filter.encapsulation) params.set('encapsulation', filter.encapsulation);
  if (filter.ipv4Address) params.set('ipv4Address', filter.ipv4Address);
  if (filter.ipv6Address) params.set('ipv6Address', filter.ipv6Address);
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countSubInterfaceActiveFilters(filter: SubInterfaceFilter): number {
  return Array.from(serializeSubInterfaceFilter(filter).keys()).length;
}
