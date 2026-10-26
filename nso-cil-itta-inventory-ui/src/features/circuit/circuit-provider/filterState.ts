import type { LifecycleStatus } from '@/shared/types';
import type { CircuitProviderFilter, CircuitProviderProviderType, CircuitProviderCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCircuitProviderQuery(filter: CircuitProviderFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.providerName) query.providerName = filter.providerName;
  if (filter.accountNumber) query.accountNumber = filter.accountNumber;
  if (filter.noc) query.noc = filter.noc;
  if (filter.escalationEmail) query.escalationEmail = filter.escalationEmail;
  if (filter.providerType?.length) query.providerType = filter.providerType.join(',');
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parseCircuitProviderFilter(params: URLSearchParams): CircuitProviderFilter {
  const filter: CircuitProviderFilter = {};
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
  const providerName = params.get('providerName');
  if (providerName) filter.providerName = providerName;
  const accountNumber = params.get('accountNumber');
  if (accountNumber) filter.accountNumber = accountNumber;
  const noc = params.get('noc');
  if (noc) filter.noc = noc;
  const escalationEmail = params.get('escalationEmail');
  if (escalationEmail) filter.escalationEmail = escalationEmail;
  const providerType = params.get('providerType');
  if (providerType) filter.providerType = providerType.split(',') as CircuitProviderProviderType[];
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as CircuitProviderCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializeCircuitProviderFilter(filter: CircuitProviderFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.providerName) params.set('providerName', filter.providerName);
  if (filter.accountNumber) params.set('accountNumber', filter.accountNumber);
  if (filter.noc) params.set('noc', filter.noc);
  if (filter.escalationEmail) params.set('escalationEmail', filter.escalationEmail);
  if (filter.providerType?.length) params.set('providerType', filter.providerType.join(','));
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countCircuitProviderActiveFilters(filter: CircuitProviderFilter): number {
  return Array.from(serializeCircuitProviderFilter(filter).keys()).length;
}
