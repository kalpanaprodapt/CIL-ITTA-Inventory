import type { LifecycleStatus } from '@/shared/types';
import type { RadioUnitFilter, RadioUnitUnitType, RadioUnitLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toRadioUnitQuery(filter: RadioUnitFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.siteId) query.siteId = filter.siteId;
  if (filter.unitType?.length) query.unitType = filter.unitType.join(',');
  if (filter.bandMhzMin !== undefined) query.bandMhzMin = filter.bandMhzMin;
  if (filter.bandMhzMax !== undefined) query.bandMhzMax = filter.bandMhzMax;
  if (filter.txPowerDbmMin !== undefined) query.txPowerDbmMin = filter.txPowerDbmMin;
  if (filter.txPowerDbmMax !== undefined) query.txPowerDbmMax = filter.txPowerDbmMax;
  if (filter.serialNumber) query.serialNumber = filter.serialNumber;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseRadioUnitFilter(params: URLSearchParams): RadioUnitFilter {
  const filter: RadioUnitFilter = {};
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
  const unitType = params.get('unitType');
  if (unitType) filter.unitType = unitType.split(',') as RadioUnitUnitType[];
  const bandMhzMin = params.get('bandMhzMin');
  if (bandMhzMin !== null && bandMhzMin !== '') filter.bandMhzMin = Number(bandMhzMin);
  const bandMhzMax = params.get('bandMhzMax');
  if (bandMhzMax !== null && bandMhzMax !== '') filter.bandMhzMax = Number(bandMhzMax);
  const txPowerDbmMin = params.get('txPowerDbmMin');
  if (txPowerDbmMin !== null && txPowerDbmMin !== '') filter.txPowerDbmMin = Number(txPowerDbmMin);
  const txPowerDbmMax = params.get('txPowerDbmMax');
  if (txPowerDbmMax !== null && txPowerDbmMax !== '') filter.txPowerDbmMax = Number(txPowerDbmMax);
  const serialNumber = params.get('serialNumber');
  if (serialNumber) filter.serialNumber = serialNumber;
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as RadioUnitLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeRadioUnitFilter(filter: RadioUnitFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.siteId) params.set('siteId', filter.siteId);
  if (filter.unitType?.length) params.set('unitType', filter.unitType.join(','));
  if (filter.bandMhzMin !== undefined) params.set('bandMhzMin', String(filter.bandMhzMin));
  if (filter.bandMhzMax !== undefined) params.set('bandMhzMax', String(filter.bandMhzMax));
  if (filter.txPowerDbmMin !== undefined) params.set('txPowerDbmMin', String(filter.txPowerDbmMin));
  if (filter.txPowerDbmMax !== undefined) params.set('txPowerDbmMax', String(filter.txPowerDbmMax));
  if (filter.serialNumber) params.set('serialNumber', filter.serialNumber);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countRadioUnitActiveFilters(filter: RadioUnitFilter): number {
  return Array.from(serializeRadioUnitFilter(filter).keys()).length;
}
