import type { LifecycleStatus } from '@/shared/types';
import type { IntegrationEndpointFilter, IntegrationEndpointAuthType, IntegrationEndpointCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toIntegrationEndpointQuery(filter: IntegrationEndpointFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.endpointName) query.endpointName = filter.endpointName;
  if (filter.targetSystem) query.targetSystem = filter.targetSystem;
  if (filter.baseUrl) query.baseUrl = filter.baseUrl;
  if (filter.authType?.length) query.authType = filter.authType.join(',');
  if (filter.timeoutMsMin !== undefined) query.timeoutMsMin = filter.timeoutMsMin;
  if (filter.timeoutMsMax !== undefined) query.timeoutMsMax = filter.timeoutMsMax;
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseIntegrationEndpointFilter(params: URLSearchParams): IntegrationEndpointFilter {
  const filter: IntegrationEndpointFilter = {};
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
  const endpointName = params.get('endpointName');
  if (endpointName) filter.endpointName = endpointName;
  const targetSystem = params.get('targetSystem');
  if (targetSystem) filter.targetSystem = targetSystem;
  const baseUrl = params.get('baseUrl');
  if (baseUrl) filter.baseUrl = baseUrl;
  const authType = params.get('authType');
  if (authType) filter.authType = authType.split(',') as IntegrationEndpointAuthType[];
  const timeoutMsMin = params.get('timeoutMsMin');
  if (timeoutMsMin !== null && timeoutMsMin !== '') filter.timeoutMsMin = Number(timeoutMsMin);
  const timeoutMsMax = params.get('timeoutMsMax');
  if (timeoutMsMax !== null && timeoutMsMax !== '') filter.timeoutMsMax = Number(timeoutMsMax);
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as IntegrationEndpointCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeIntegrationEndpointFilter(filter: IntegrationEndpointFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.endpointName) params.set('endpointName', filter.endpointName);
  if (filter.targetSystem) params.set('targetSystem', filter.targetSystem);
  if (filter.baseUrl) params.set('baseUrl', filter.baseUrl);
  if (filter.authType?.length) params.set('authType', filter.authType.join(','));
  if (filter.timeoutMsMin !== undefined) params.set('timeoutMsMin', String(filter.timeoutMsMin));
  if (filter.timeoutMsMax !== undefined) params.set('timeoutMsMax', String(filter.timeoutMsMax));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countIntegrationEndpointActiveFilters(filter: IntegrationEndpointFilter): number {
  return Array.from(serializeIntegrationEndpointFilter(filter).keys()).length;
}
