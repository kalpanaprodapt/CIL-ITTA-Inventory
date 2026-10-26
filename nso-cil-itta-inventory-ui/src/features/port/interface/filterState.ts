import type { LifecycleStatus } from '@/shared/types';
import type { InterfaceFilter, InterfaceOperState, InterfaceCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toInterfaceQuery(filter: InterfaceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.portId) query.portId = filter.portId;
  if (filter.ifName) query.ifName = filter.ifName;
  if (filter.ifIndexMin !== undefined) query.ifIndexMin = filter.ifIndexMin;
  if (filter.ifIndexMax !== undefined) query.ifIndexMax = filter.ifIndexMax;
  if (filter.macAddress) query.macAddress = filter.macAddress;
  if (filter.mtuMin !== undefined) query.mtuMin = filter.mtuMin;
  if (filter.mtuMax !== undefined) query.mtuMax = filter.mtuMax;
  if (filter.operState?.length) query.operState = filter.operState.join(',');
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parseInterfaceFilter(params: URLSearchParams): InterfaceFilter {
  const filter: InterfaceFilter = {};
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
  const portId = params.get('portId');
  if (portId) filter.portId = portId;
  const ifName = params.get('ifName');
  if (ifName) filter.ifName = ifName;
  const ifIndexMin = params.get('ifIndexMin');
  if (ifIndexMin !== null && ifIndexMin !== '') filter.ifIndexMin = Number(ifIndexMin);
  const ifIndexMax = params.get('ifIndexMax');
  if (ifIndexMax !== null && ifIndexMax !== '') filter.ifIndexMax = Number(ifIndexMax);
  const macAddress = params.get('macAddress');
  if (macAddress) filter.macAddress = macAddress;
  const mtuMin = params.get('mtuMin');
  if (mtuMin !== null && mtuMin !== '') filter.mtuMin = Number(mtuMin);
  const mtuMax = params.get('mtuMax');
  if (mtuMax !== null && mtuMax !== '') filter.mtuMax = Number(mtuMax);
  const operState = params.get('operState');
  if (operState) filter.operState = operState.split(',') as InterfaceOperState[];
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as InterfaceCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializeInterfaceFilter(filter: InterfaceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.portId) params.set('portId', filter.portId);
  if (filter.ifName) params.set('ifName', filter.ifName);
  if (filter.ifIndexMin !== undefined) params.set('ifIndexMin', String(filter.ifIndexMin));
  if (filter.ifIndexMax !== undefined) params.set('ifIndexMax', String(filter.ifIndexMax));
  if (filter.macAddress) params.set('macAddress', filter.macAddress);
  if (filter.mtuMin !== undefined) params.set('mtuMin', String(filter.mtuMin));
  if (filter.mtuMax !== undefined) params.set('mtuMax', String(filter.mtuMax));
  if (filter.operState?.length) params.set('operState', filter.operState.join(','));
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countInterfaceActiveFilters(filter: InterfaceFilter): number {
  return Array.from(serializeInterfaceFilter(filter).keys()).length;
}
