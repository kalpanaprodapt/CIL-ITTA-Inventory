import type { LifecycleStatus } from '@/shared/types';
import type { RoadmFilter, RoadmCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toRoadmQuery(filter: RoadmFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.systemId) query.systemId = filter.systemId;
  if (filter.degreesMin !== undefined) query.degreesMin = filter.degreesMin;
  if (filter.degreesMax !== undefined) query.degreesMax = filter.degreesMax;
  if (filter.colorless !== undefined) query.colorless = filter.colorless;
  if (filter.directionless !== undefined) query.directionless = filter.directionless;
  if (filter.contentionless !== undefined) query.contentionless = filter.contentionless;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseRoadmFilter(params: URLSearchParams): RoadmFilter {
  const filter: RoadmFilter = {};
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
  const systemId = params.get('systemId');
  if (systemId) filter.systemId = systemId;
  const degreesMin = params.get('degreesMin');
  if (degreesMin !== null && degreesMin !== '') filter.degreesMin = Number(degreesMin);
  const degreesMax = params.get('degreesMax');
  if (degreesMax !== null && degreesMax !== '') filter.degreesMax = Number(degreesMax);
  const colorless = params.get('colorless');
  if (colorless !== null) filter.colorless = colorless === 'true';
  const directionless = params.get('directionless');
  if (directionless !== null) filter.directionless = directionless === 'true';
  const contentionless = params.get('contentionless');
  if (contentionless !== null) filter.contentionless = contentionless === 'true';
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as RoadmCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeRoadmFilter(filter: RoadmFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.systemId) params.set('systemId', filter.systemId);
  if (filter.degreesMin !== undefined) params.set('degreesMin', String(filter.degreesMin));
  if (filter.degreesMax !== undefined) params.set('degreesMax', String(filter.degreesMax));
  if (filter.colorless !== undefined) params.set('colorless', String(filter.colorless));
  if (filter.directionless !== undefined) params.set('directionless', String(filter.directionless));
  if (filter.contentionless !== undefined) params.set('contentionless', String(filter.contentionless));
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countRoadmActiveFilters(filter: RoadmFilter): number {
  return Array.from(serializeRoadmFilter(filter).keys()).length;
}
