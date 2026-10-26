import type { LifecycleStatus } from '@/shared/types';
import type { ServiceEndpointFilter, ServiceEndpointEndpointRole, ServiceEndpointLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toServiceEndpointQuery(filter: ServiceEndpointFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.serviceId) query.serviceId = filter.serviceId;
  if (filter.siteId) query.siteId = filter.siteId;
  if (filter.portId) query.portId = filter.portId;
  if (filter.endpointRole?.length) query.endpointRole = filter.endpointRole.join(',');
  if (filter.handoff) query.handoff = filter.handoff;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseServiceEndpointFilter(params: URLSearchParams): ServiceEndpointFilter {
  const filter: ServiceEndpointFilter = {};
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
  const serviceId = params.get('serviceId');
  if (serviceId) filter.serviceId = serviceId;
  const siteId = params.get('siteId');
  if (siteId) filter.siteId = siteId;
  const portId = params.get('portId');
  if (portId) filter.portId = portId;
  const endpointRole = params.get('endpointRole');
  if (endpointRole) filter.endpointRole = endpointRole.split(',') as ServiceEndpointEndpointRole[];
  const handoff = params.get('handoff');
  if (handoff) filter.handoff = handoff;
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
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as ServiceEndpointLifecycleStage[];
  return filter;
}

export function serializeServiceEndpointFilter(filter: ServiceEndpointFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.serviceId) params.set('serviceId', filter.serviceId);
  if (filter.siteId) params.set('siteId', filter.siteId);
  if (filter.portId) params.set('portId', filter.portId);
  if (filter.endpointRole?.length) params.set('endpointRole', filter.endpointRole.join(','));
  if (filter.handoff) params.set('handoff', filter.handoff);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countServiceEndpointActiveFilters(filter: ServiceEndpointFilter): number {
  return Array.from(serializeServiceEndpointFilter(filter).keys()).length;
}
