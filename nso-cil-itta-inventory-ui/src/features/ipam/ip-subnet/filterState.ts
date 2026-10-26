import type { LifecycleStatus } from '@/shared/types';
import type { IpSubnetFilter, IpSubnetIpVersion, IpSubnetCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toIpSubnetQuery(filter: IpSubnetFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.cidr) query.cidr = filter.cidr;
  if (filter.ipVersion?.length) query.ipVersion = filter.ipVersion.join(',');
  if (filter.vrfId) query.vrfId = filter.vrfId;
  if (filter.gateway) query.gateway = filter.gateway;
  if (filter.vlanId) query.vlanId = filter.vlanId;
  if (filter.utilizationPctMin !== undefined) query.utilizationPctMin = filter.utilizationPctMin;
  if (filter.utilizationPctMax !== undefined) query.utilizationPctMax = filter.utilizationPctMax;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parseIpSubnetFilter(params: URLSearchParams): IpSubnetFilter {
  const filter: IpSubnetFilter = {};
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
  const cidr = params.get('cidr');
  if (cidr) filter.cidr = cidr;
  const ipVersion = params.get('ipVersion');
  if (ipVersion) filter.ipVersion = ipVersion.split(',') as IpSubnetIpVersion[];
  const vrfId = params.get('vrfId');
  if (vrfId) filter.vrfId = vrfId;
  const gateway = params.get('gateway');
  if (gateway) filter.gateway = gateway;
  const vlanId = params.get('vlanId');
  if (vlanId) filter.vlanId = vlanId;
  const utilizationPctMin = params.get('utilizationPctMin');
  if (utilizationPctMin !== null && utilizationPctMin !== '') filter.utilizationPctMin = Number(utilizationPctMin);
  const utilizationPctMax = params.get('utilizationPctMax');
  if (utilizationPctMax !== null && utilizationPctMax !== '') filter.utilizationPctMax = Number(utilizationPctMax);
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as IpSubnetCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializeIpSubnetFilter(filter: IpSubnetFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.cidr) params.set('cidr', filter.cidr);
  if (filter.ipVersion?.length) params.set('ipVersion', filter.ipVersion.join(','));
  if (filter.vrfId) params.set('vrfId', filter.vrfId);
  if (filter.gateway) params.set('gateway', filter.gateway);
  if (filter.vlanId) params.set('vlanId', filter.vlanId);
  if (filter.utilizationPctMin !== undefined) params.set('utilizationPctMin', String(filter.utilizationPctMin));
  if (filter.utilizationPctMax !== undefined) params.set('utilizationPctMax', String(filter.utilizationPctMax));
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countIpSubnetActiveFilters(filter: IpSubnetFilter): number {
  return Array.from(serializeIpSubnetFilter(filter).keys()).length;
}
