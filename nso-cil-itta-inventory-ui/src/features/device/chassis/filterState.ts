import type { LifecycleStatus } from '@/shared/types';
import type { ChassisFilter, ChassisLifecycleStage, ChassisCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toChassisQuery(filter: ChassisFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.deviceId) query.deviceId = filter.deviceId;
  if (filter.slotCountMin !== undefined) query.slotCountMin = filter.slotCountMin;
  if (filter.slotCountMax !== undefined) query.slotCountMax = filter.slotCountMax;
  if (filter.serialNumber) query.serialNumber = filter.serialNumber;
  if (filter.backplaneGbpsMin !== undefined) query.backplaneGbpsMin = filter.backplaneGbpsMin;
  if (filter.backplaneGbpsMax !== undefined) query.backplaneGbpsMax = filter.backplaneGbpsMax;
  if (filter.redundancyMode) query.redundancyMode = filter.redundancyMode;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  return query;
}

export function parseChassisFilter(params: URLSearchParams): ChassisFilter {
  const filter: ChassisFilter = {};
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
  const deviceId = params.get('deviceId');
  if (deviceId) filter.deviceId = deviceId;
  const slotCountMin = params.get('slotCountMin');
  if (slotCountMin !== null && slotCountMin !== '') filter.slotCountMin = Number(slotCountMin);
  const slotCountMax = params.get('slotCountMax');
  if (slotCountMax !== null && slotCountMax !== '') filter.slotCountMax = Number(slotCountMax);
  const serialNumber = params.get('serialNumber');
  if (serialNumber) filter.serialNumber = serialNumber;
  const backplaneGbpsMin = params.get('backplaneGbpsMin');
  if (backplaneGbpsMin !== null && backplaneGbpsMin !== '') filter.backplaneGbpsMin = Number(backplaneGbpsMin);
  const backplaneGbpsMax = params.get('backplaneGbpsMax');
  if (backplaneGbpsMax !== null && backplaneGbpsMax !== '') filter.backplaneGbpsMax = Number(backplaneGbpsMax);
  const redundancyMode = params.get('redundancyMode');
  if (redundancyMode) filter.redundancyMode = redundancyMode;
  const lifecycleStage = params.get('lifecycleStage');
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as ChassisLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as ChassisCriticality[];
  return filter;
}

export function serializeChassisFilter(filter: ChassisFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.deviceId) params.set('deviceId', filter.deviceId);
  if (filter.slotCountMin !== undefined) params.set('slotCountMin', String(filter.slotCountMin));
  if (filter.slotCountMax !== undefined) params.set('slotCountMax', String(filter.slotCountMax));
  if (filter.serialNumber) params.set('serialNumber', filter.serialNumber);
  if (filter.backplaneGbpsMin !== undefined) params.set('backplaneGbpsMin', String(filter.backplaneGbpsMin));
  if (filter.backplaneGbpsMax !== undefined) params.set('backplaneGbpsMax', String(filter.backplaneGbpsMax));
  if (filter.redundancyMode) params.set('redundancyMode', filter.redundancyMode);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  return params;
}

export function countChassisActiveFilters(filter: ChassisFilter): number {
  return Array.from(serializeChassisFilter(filter).keys()).length;
}
