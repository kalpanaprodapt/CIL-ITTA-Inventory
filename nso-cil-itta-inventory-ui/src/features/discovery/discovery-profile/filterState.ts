import type { LifecycleStatus } from '@/shared/types';
import type { DiscoveryProfileFilter, DiscoveryProfileProtocol } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toDiscoveryProfileQuery(filter: DiscoveryProfileFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.profileName) query.profileName = filter.profileName;
  if (filter.protocol?.length) query.protocol = filter.protocol.join(',');
  if (filter.credentialRef) query.credentialRef = filter.credentialRef;
  if (filter.scopeCidr) query.scopeCidr = filter.scopeCidr;
  if (filter.scheduleCron) query.scheduleCron = filter.scheduleCron;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseDiscoveryProfileFilter(params: URLSearchParams): DiscoveryProfileFilter {
  const filter: DiscoveryProfileFilter = {};
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
  const profileName = params.get('profileName');
  if (profileName) filter.profileName = profileName;
  const protocol = params.get('protocol');
  if (protocol) filter.protocol = protocol.split(',') as DiscoveryProfileProtocol[];
  const credentialRef = params.get('credentialRef');
  if (credentialRef) filter.credentialRef = credentialRef;
  const scopeCidr = params.get('scopeCidr');
  if (scopeCidr) filter.scopeCidr = scopeCidr;
  const scheduleCron = params.get('scheduleCron');
  if (scheduleCron) filter.scheduleCron = scheduleCron;
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

export function serializeDiscoveryProfileFilter(filter: DiscoveryProfileFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.profileName) params.set('profileName', filter.profileName);
  if (filter.protocol?.length) params.set('protocol', filter.protocol.join(','));
  if (filter.credentialRef) params.set('credentialRef', filter.credentialRef);
  if (filter.scopeCidr) params.set('scopeCidr', filter.scopeCidr);
  if (filter.scheduleCron) params.set('scheduleCron', filter.scheduleCron);
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countDiscoveryProfileActiveFilters(filter: DiscoveryProfileFilter): number {
  return Array.from(serializeDiscoveryProfileFilter(filter).keys()).length;
}
