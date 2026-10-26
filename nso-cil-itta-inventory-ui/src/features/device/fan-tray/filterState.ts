import type { LifecycleStatus } from '@/shared/types';
import type { FanTrayFilter, FanTrayAirflow, FanTrayLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toFanTrayQuery(filter: FanTrayFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.chassisId) query.chassisId = filter.chassisId;
  if (filter.slotNumberMin !== undefined) query.slotNumberMin = filter.slotNumberMin;
  if (filter.slotNumberMax !== undefined) query.slotNumberMax = filter.slotNumberMax;
  if (filter.fanCountMin !== undefined) query.fanCountMin = filter.fanCountMin;
  if (filter.fanCountMax !== undefined) query.fanCountMax = filter.fanCountMax;
  if (filter.airflow?.length) query.airflow = filter.airflow.join(',');
  if (filter.serialNumber) query.serialNumber = filter.serialNumber;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  return query;
}

export function parseFanTrayFilter(params: URLSearchParams): FanTrayFilter {
  const filter: FanTrayFilter = {};
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
  const chassisId = params.get('chassisId');
  if (chassisId) filter.chassisId = chassisId;
  const slotNumberMin = params.get('slotNumberMin');
  if (slotNumberMin !== null && slotNumberMin !== '') filter.slotNumberMin = Number(slotNumberMin);
  const slotNumberMax = params.get('slotNumberMax');
  if (slotNumberMax !== null && slotNumberMax !== '') filter.slotNumberMax = Number(slotNumberMax);
  const fanCountMin = params.get('fanCountMin');
  if (fanCountMin !== null && fanCountMin !== '') filter.fanCountMin = Number(fanCountMin);
  const fanCountMax = params.get('fanCountMax');
  if (fanCountMax !== null && fanCountMax !== '') filter.fanCountMax = Number(fanCountMax);
  const airflow = params.get('airflow');
  if (airflow) filter.airflow = airflow.split(',') as FanTrayAirflow[];
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as FanTrayLifecycleStage[];
  return filter;
}

export function serializeFanTrayFilter(filter: FanTrayFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.chassisId) params.set('chassisId', filter.chassisId);
  if (filter.slotNumberMin !== undefined) params.set('slotNumberMin', String(filter.slotNumberMin));
  if (filter.slotNumberMax !== undefined) params.set('slotNumberMax', String(filter.slotNumberMax));
  if (filter.fanCountMin !== undefined) params.set('fanCountMin', String(filter.fanCountMin));
  if (filter.fanCountMax !== undefined) params.set('fanCountMax', String(filter.fanCountMax));
  if (filter.airflow?.length) params.set('airflow', filter.airflow.join(','));
  if (filter.serialNumber) params.set('serialNumber', filter.serialNumber);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  return params;
}

export function countFanTrayActiveFilters(filter: FanTrayFilter): number {
  return Array.from(serializeFanTrayFilter(filter).keys()).length;
}
