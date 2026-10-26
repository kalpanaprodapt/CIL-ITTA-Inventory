import type { LifecycleStatus } from '@/shared/types';
import type { VpnServiceFilter, VpnServiceVpnType, VpnServiceTopology, VpnServiceCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toVpnServiceQuery(filter: VpnServiceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.vpnName) query.vpnName = filter.vpnName;
  if (filter.vpnType?.length) query.vpnType = filter.vpnType.join(',');
  if (filter.vrfId) query.vrfId = filter.vrfId;
  if (filter.customerId) query.customerId = filter.customerId;
  if (filter.topology?.length) query.topology = filter.topology.join(',');
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parseVpnServiceFilter(params: URLSearchParams): VpnServiceFilter {
  const filter: VpnServiceFilter = {};
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
  const vpnName = params.get('vpnName');
  if (vpnName) filter.vpnName = vpnName;
  const vpnType = params.get('vpnType');
  if (vpnType) filter.vpnType = vpnType.split(',') as VpnServiceVpnType[];
  const vrfId = params.get('vrfId');
  if (vrfId) filter.vrfId = vrfId;
  const customerId = params.get('customerId');
  if (customerId) filter.customerId = customerId;
  const topology = params.get('topology');
  if (topology) filter.topology = topology.split(',') as VpnServiceTopology[];
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as VpnServiceCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializeVpnServiceFilter(filter: VpnServiceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.vpnName) params.set('vpnName', filter.vpnName);
  if (filter.vpnType?.length) params.set('vpnType', filter.vpnType.join(','));
  if (filter.vrfId) params.set('vrfId', filter.vrfId);
  if (filter.customerId) params.set('customerId', filter.customerId);
  if (filter.topology?.length) params.set('topology', filter.topology.join(','));
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countVpnServiceActiveFilters(filter: VpnServiceFilter): number {
  return Array.from(serializeVpnServiceFilter(filter).keys()).length;
}
