import type { LifecycleStatus } from '@/shared/types';
import type { BaseStationFilter, BaseStationTechnology, BaseStationLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toBaseStationQuery(filter: BaseStationFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.siteId) query.siteId = filter.siteId;
  if (filter.enbIdMin !== undefined) query.enbIdMin = filter.enbIdMin;
  if (filter.enbIdMax !== undefined) query.enbIdMax = filter.enbIdMax;
  if (filter.technology?.length) query.technology = filter.technology.join(',');
  if (filter.sectorCountMin !== undefined) query.sectorCountMin = filter.sectorCountMin;
  if (filter.sectorCountMax !== undefined) query.sectorCountMax = filter.sectorCountMax;
  if (filter.cellCountMin !== undefined) query.cellCountMin = filter.cellCountMin;
  if (filter.cellCountMax !== undefined) query.cellCountMax = filter.cellCountMax;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseBaseStationFilter(params: URLSearchParams): BaseStationFilter {
  const filter: BaseStationFilter = {};
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
  const siteId = params.get('siteId');
  if (siteId) filter.siteId = siteId;
  const enbIdMin = params.get('enbIdMin');
  if (enbIdMin !== null && enbIdMin !== '') filter.enbIdMin = Number(enbIdMin);
  const enbIdMax = params.get('enbIdMax');
  if (enbIdMax !== null && enbIdMax !== '') filter.enbIdMax = Number(enbIdMax);
  const technology = params.get('technology');
  if (technology) filter.technology = technology.split(',') as BaseStationTechnology[];
  const sectorCountMin = params.get('sectorCountMin');
  if (sectorCountMin !== null && sectorCountMin !== '') filter.sectorCountMin = Number(sectorCountMin);
  const sectorCountMax = params.get('sectorCountMax');
  if (sectorCountMax !== null && sectorCountMax !== '') filter.sectorCountMax = Number(sectorCountMax);
  const cellCountMin = params.get('cellCountMin');
  if (cellCountMin !== null && cellCountMin !== '') filter.cellCountMin = Number(cellCountMin);
  const cellCountMax = params.get('cellCountMax');
  if (cellCountMax !== null && cellCountMax !== '') filter.cellCountMax = Number(cellCountMax);
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as BaseStationLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeBaseStationFilter(filter: BaseStationFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.siteId) params.set('siteId', filter.siteId);
  if (filter.enbIdMin !== undefined) params.set('enbIdMin', String(filter.enbIdMin));
  if (filter.enbIdMax !== undefined) params.set('enbIdMax', String(filter.enbIdMax));
  if (filter.technology?.length) params.set('technology', filter.technology.join(','));
  if (filter.sectorCountMin !== undefined) params.set('sectorCountMin', String(filter.sectorCountMin));
  if (filter.sectorCountMax !== undefined) params.set('sectorCountMax', String(filter.sectorCountMax));
  if (filter.cellCountMin !== undefined) params.set('cellCountMin', String(filter.cellCountMin));
  if (filter.cellCountMax !== undefined) params.set('cellCountMax', String(filter.cellCountMax));
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countBaseStationActiveFilters(filter: BaseStationFilter): number {
  return Array.from(serializeBaseStationFilter(filter).keys()).length;
}
