import type { LifecycleStatus } from '@/shared/types';
import type { HardwareModelFilter, HardwareModelLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toHardwareModelQuery(filter: HardwareModelFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.vendorId) query.vendorId = filter.vendorId;
  if (filter.modelNumber) query.modelNumber = filter.modelNumber;
  if (filter.formFactor) query.formFactor = filter.formFactor;
  if (filter.powerWattsMin !== undefined) query.powerWattsMin = filter.powerWattsMin;
  if (filter.powerWattsMax !== undefined) query.powerWattsMax = filter.powerWattsMax;
  if (filter.eolDateFrom) query.eolDateFrom = filter.eolDateFrom;
  if (filter.eolDateTo) query.eolDateTo = filter.eolDateTo;
  if (filter.eosDateFrom) query.eosDateFrom = filter.eosDateFrom;
  if (filter.eosDateTo) query.eosDateTo = filter.eosDateTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  return query;
}

export function parseHardwareModelFilter(params: URLSearchParams): HardwareModelFilter {
  const filter: HardwareModelFilter = {};
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
  const vendorId = params.get('vendorId');
  if (vendorId) filter.vendorId = vendorId;
  const modelNumber = params.get('modelNumber');
  if (modelNumber) filter.modelNumber = modelNumber;
  const formFactor = params.get('formFactor');
  if (formFactor) filter.formFactor = formFactor;
  const powerWattsMin = params.get('powerWattsMin');
  if (powerWattsMin !== null && powerWattsMin !== '') filter.powerWattsMin = Number(powerWattsMin);
  const powerWattsMax = params.get('powerWattsMax');
  if (powerWattsMax !== null && powerWattsMax !== '') filter.powerWattsMax = Number(powerWattsMax);
  const eolDateFrom = params.get('eolDateFrom');
  if (eolDateFrom) filter.eolDateFrom = eolDateFrom;
  const eolDateTo = params.get('eolDateTo');
  if (eolDateTo) filter.eolDateTo = eolDateTo;
  const eosDateFrom = params.get('eosDateFrom');
  if (eosDateFrom) filter.eosDateFrom = eosDateFrom;
  const eosDateTo = params.get('eosDateTo');
  if (eosDateTo) filter.eosDateTo = eosDateTo;
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as HardwareModelLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  return filter;
}

export function serializeHardwareModelFilter(filter: HardwareModelFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.vendorId) params.set('vendorId', filter.vendorId);
  if (filter.modelNumber) params.set('modelNumber', filter.modelNumber);
  if (filter.formFactor) params.set('formFactor', filter.formFactor);
  if (filter.powerWattsMin !== undefined) params.set('powerWattsMin', String(filter.powerWattsMin));
  if (filter.powerWattsMax !== undefined) params.set('powerWattsMax', String(filter.powerWattsMax));
  if (filter.eolDateFrom) params.set('eolDateFrom', filter.eolDateFrom);
  if (filter.eolDateTo) params.set('eolDateTo', filter.eolDateTo);
  if (filter.eosDateFrom) params.set('eosDateFrom', filter.eosDateFrom);
  if (filter.eosDateTo) params.set('eosDateTo', filter.eosDateTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  return params;
}

export function countHardwareModelActiveFilters(filter: HardwareModelFilter): number {
  return Array.from(serializeHardwareModelFilter(filter).keys()).length;
}
