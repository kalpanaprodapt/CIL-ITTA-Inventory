import type { LifecycleStatus } from '@/shared/types';
import type { CircuitFilter, CircuitCircuitType, CircuitLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCircuitQuery(filter: CircuitFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.circuitId) query.circuitId = filter.circuitId;
  if (filter.circuitType?.length) query.circuitType = filter.circuitType.join(',');
  if (filter.bandwidthMbpsMin !== undefined) query.bandwidthMbpsMin = filter.bandwidthMbpsMin;
  if (filter.bandwidthMbpsMax !== undefined) query.bandwidthMbpsMax = filter.bandwidthMbpsMax;
  if (filter.providerId) query.providerId = filter.providerId;
  if (filter.aSiteId) query.aSiteId = filter.aSiteId;
  if (filter.zSiteId) query.zSiteId = filter.zSiteId;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseCircuitFilter(params: URLSearchParams): CircuitFilter {
  const filter: CircuitFilter = {};
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
  const circuitId = params.get('circuitId');
  if (circuitId) filter.circuitId = circuitId;
  const circuitType = params.get('circuitType');
  if (circuitType) filter.circuitType = circuitType.split(',') as CircuitCircuitType[];
  const bandwidthMbpsMin = params.get('bandwidthMbpsMin');
  if (bandwidthMbpsMin !== null && bandwidthMbpsMin !== '') filter.bandwidthMbpsMin = Number(bandwidthMbpsMin);
  const bandwidthMbpsMax = params.get('bandwidthMbpsMax');
  if (bandwidthMbpsMax !== null && bandwidthMbpsMax !== '') filter.bandwidthMbpsMax = Number(bandwidthMbpsMax);
  const providerId = params.get('providerId');
  if (providerId) filter.providerId = providerId;
  const aSiteId = params.get('aSiteId');
  if (aSiteId) filter.aSiteId = aSiteId;
  const zSiteId = params.get('zSiteId');
  if (zSiteId) filter.zSiteId = zSiteId;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as CircuitLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeCircuitFilter(filter: CircuitFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.circuitId) params.set('circuitId', filter.circuitId);
  if (filter.circuitType?.length) params.set('circuitType', filter.circuitType.join(','));
  if (filter.bandwidthMbpsMin !== undefined) params.set('bandwidthMbpsMin', String(filter.bandwidthMbpsMin));
  if (filter.bandwidthMbpsMax !== undefined) params.set('bandwidthMbpsMax', String(filter.bandwidthMbpsMax));
  if (filter.providerId) params.set('providerId', filter.providerId);
  if (filter.aSiteId) params.set('aSiteId', filter.aSiteId);
  if (filter.zSiteId) params.set('zSiteId', filter.zSiteId);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countCircuitActiveFilters(filter: CircuitFilter): number {
  return Array.from(serializeCircuitFilter(filter).keys()).length;
}
