import type { LifecycleStatus } from '@/shared/types';
import type { PowerSupplyFilter, PowerSupplyCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toPowerSupplyQuery(filter: PowerSupplyFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.chassisId) query.chassisId = filter.chassisId;
  if (filter.slotNumberMin !== undefined) query.slotNumberMin = filter.slotNumberMin;
  if (filter.slotNumberMax !== undefined) query.slotNumberMax = filter.slotNumberMax;
  if (filter.wattageMin !== undefined) query.wattageMin = filter.wattageMin;
  if (filter.wattageMax !== undefined) query.wattageMax = filter.wattageMax;
  if (filter.feedId) query.feedId = filter.feedId;
  if (filter.serialNumber) query.serialNumber = filter.serialNumber;
  if (filter.efficiencyMin !== undefined) query.efficiencyMin = filter.efficiencyMin;
  if (filter.efficiencyMax !== undefined) query.efficiencyMax = filter.efficiencyMax;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  return query;
}

export function parsePowerSupplyFilter(params: URLSearchParams): PowerSupplyFilter {
  const filter: PowerSupplyFilter = {};
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
  const wattageMin = params.get('wattageMin');
  if (wattageMin !== null && wattageMin !== '') filter.wattageMin = Number(wattageMin);
  const wattageMax = params.get('wattageMax');
  if (wattageMax !== null && wattageMax !== '') filter.wattageMax = Number(wattageMax);
  const feedId = params.get('feedId');
  if (feedId) filter.feedId = feedId;
  const serialNumber = params.get('serialNumber');
  if (serialNumber) filter.serialNumber = serialNumber;
  const efficiencyMin = params.get('efficiencyMin');
  if (efficiencyMin !== null && efficiencyMin !== '') filter.efficiencyMin = Number(efficiencyMin);
  const efficiencyMax = params.get('efficiencyMax');
  if (efficiencyMax !== null && efficiencyMax !== '') filter.efficiencyMax = Number(efficiencyMax);
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as PowerSupplyCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  const effectiveToFrom = params.get('effectiveToFrom');
  if (effectiveToFrom) filter.effectiveToFrom = effectiveToFrom;
  const effectiveToTo = params.get('effectiveToTo');
  if (effectiveToTo) filter.effectiveToTo = effectiveToTo;
  return filter;
}

export function serializePowerSupplyFilter(filter: PowerSupplyFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.chassisId) params.set('chassisId', filter.chassisId);
  if (filter.slotNumberMin !== undefined) params.set('slotNumberMin', String(filter.slotNumberMin));
  if (filter.slotNumberMax !== undefined) params.set('slotNumberMax', String(filter.slotNumberMax));
  if (filter.wattageMin !== undefined) params.set('wattageMin', String(filter.wattageMin));
  if (filter.wattageMax !== undefined) params.set('wattageMax', String(filter.wattageMax));
  if (filter.feedId) params.set('feedId', filter.feedId);
  if (filter.serialNumber) params.set('serialNumber', filter.serialNumber);
  if (filter.efficiencyMin !== undefined) params.set('efficiencyMin', String(filter.efficiencyMin));
  if (filter.efficiencyMax !== undefined) params.set('efficiencyMax', String(filter.efficiencyMax));
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  return params;
}

export function countPowerSupplyActiveFilters(filter: PowerSupplyFilter): number {
  return Array.from(serializePowerSupplyFilter(filter).keys()).length;
}
