import type { LifecycleStatus } from '@/shared/types';
import type { SpliceFilter, SpliceLifecycleStage, SpliceCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSpliceQuery(filter: SpliceFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.enclosureId) query.enclosureId = filter.enclosureId;
  if (filter.aStrandId) query.aStrandId = filter.aStrandId;
  if (filter.zStrandId) query.zStrandId = filter.zStrandId;
  if (filter.lossDbMin !== undefined) query.lossDbMin = filter.lossDbMin;
  if (filter.lossDbMax !== undefined) query.lossDbMax = filter.lossDbMax;
  if (filter.splicedOnFrom) query.splicedOnFrom = filter.splicedOnFrom;
  if (filter.splicedOnTo) query.splicedOnTo = filter.splicedOnTo;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parseSpliceFilter(params: URLSearchParams): SpliceFilter {
  const filter: SpliceFilter = {};
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
  const enclosureId = params.get('enclosureId');
  if (enclosureId) filter.enclosureId = enclosureId;
  const aStrandId = params.get('aStrandId');
  if (aStrandId) filter.aStrandId = aStrandId;
  const zStrandId = params.get('zStrandId');
  if (zStrandId) filter.zStrandId = zStrandId;
  const lossDbMin = params.get('lossDbMin');
  if (lossDbMin !== null && lossDbMin !== '') filter.lossDbMin = Number(lossDbMin);
  const lossDbMax = params.get('lossDbMax');
  if (lossDbMax !== null && lossDbMax !== '') filter.lossDbMax = Number(lossDbMax);
  const splicedOnFrom = params.get('splicedOnFrom');
  if (splicedOnFrom) filter.splicedOnFrom = splicedOnFrom;
  const splicedOnTo = params.get('splicedOnTo');
  if (splicedOnTo) filter.splicedOnTo = splicedOnTo;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as SpliceLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as SpliceCriticality[];
  return filter;
}

export function serializeSpliceFilter(filter: SpliceFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.enclosureId) params.set('enclosureId', filter.enclosureId);
  if (filter.aStrandId) params.set('aStrandId', filter.aStrandId);
  if (filter.zStrandId) params.set('zStrandId', filter.zStrandId);
  if (filter.lossDbMin !== undefined) params.set('lossDbMin', String(filter.lossDbMin));
  if (filter.lossDbMax !== undefined) params.set('lossDbMax', String(filter.lossDbMax));
  if (filter.splicedOnFrom) params.set('splicedOnFrom', filter.splicedOnFrom);
  if (filter.splicedOnTo) params.set('splicedOnTo', filter.splicedOnTo);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countSpliceActiveFilters(filter: SpliceFilter): number {
  return Array.from(serializeSpliceFilter(filter).keys()).length;
}
