import type { LifecycleStatus } from '@/shared/types';
import type { MplsLspFilter, MplsLspPathType, MplsLspCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toMplsLspQuery(filter: MplsLspFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.lspName) query.lspName = filter.lspName;
  if (filter.headEndId) query.headEndId = filter.headEndId;
  if (filter.tailEndId) query.tailEndId = filter.tailEndId;
  if (filter.bandwidthMbpsMin !== undefined) query.bandwidthMbpsMin = filter.bandwidthMbpsMin;
  if (filter.bandwidthMbpsMax !== undefined) query.bandwidthMbpsMax = filter.bandwidthMbpsMax;
  if (filter.setupPriorityMin !== undefined) query.setupPriorityMin = filter.setupPriorityMin;
  if (filter.setupPriorityMax !== undefined) query.setupPriorityMax = filter.setupPriorityMax;
  if (filter.pathType?.length) query.pathType = filter.pathType.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseMplsLspFilter(params: URLSearchParams): MplsLspFilter {
  const filter: MplsLspFilter = {};
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
  const lspName = params.get('lspName');
  if (lspName) filter.lspName = lspName;
  const headEndId = params.get('headEndId');
  if (headEndId) filter.headEndId = headEndId;
  const tailEndId = params.get('tailEndId');
  if (tailEndId) filter.tailEndId = tailEndId;
  const bandwidthMbpsMin = params.get('bandwidthMbpsMin');
  if (bandwidthMbpsMin !== null && bandwidthMbpsMin !== '') filter.bandwidthMbpsMin = Number(bandwidthMbpsMin);
  const bandwidthMbpsMax = params.get('bandwidthMbpsMax');
  if (bandwidthMbpsMax !== null && bandwidthMbpsMax !== '') filter.bandwidthMbpsMax = Number(bandwidthMbpsMax);
  const setupPriorityMin = params.get('setupPriorityMin');
  if (setupPriorityMin !== null && setupPriorityMin !== '') filter.setupPriorityMin = Number(setupPriorityMin);
  const setupPriorityMax = params.get('setupPriorityMax');
  if (setupPriorityMax !== null && setupPriorityMax !== '') filter.setupPriorityMax = Number(setupPriorityMax);
  const pathType = params.get('pathType');
  if (pathType) filter.pathType = pathType.split(',') as MplsLspPathType[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as MplsLspCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeMplsLspFilter(filter: MplsLspFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.lspName) params.set('lspName', filter.lspName);
  if (filter.headEndId) params.set('headEndId', filter.headEndId);
  if (filter.tailEndId) params.set('tailEndId', filter.tailEndId);
  if (filter.bandwidthMbpsMin !== undefined) params.set('bandwidthMbpsMin', String(filter.bandwidthMbpsMin));
  if (filter.bandwidthMbpsMax !== undefined) params.set('bandwidthMbpsMax', String(filter.bandwidthMbpsMax));
  if (filter.setupPriorityMin !== undefined) params.set('setupPriorityMin', String(filter.setupPriorityMin));
  if (filter.setupPriorityMax !== undefined) params.set('setupPriorityMax', String(filter.setupPriorityMax));
  if (filter.pathType?.length) params.set('pathType', filter.pathType.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countMplsLspActiveFilters(filter: MplsLspFilter): number {
  return Array.from(serializeMplsLspFilter(filter).keys()).length;
}
