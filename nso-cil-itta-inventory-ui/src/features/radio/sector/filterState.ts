import type { LifecycleStatus } from '@/shared/types';
import type { SectorFilter, SectorCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toSectorQuery(filter: SectorFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.baseStationId) query.baseStationId = filter.baseStationId;
  if (filter.sectorNumberMin !== undefined) query.sectorNumberMin = filter.sectorNumberMin;
  if (filter.sectorNumberMax !== undefined) query.sectorNumberMax = filter.sectorNumberMax;
  if (filter.pciMin !== undefined) query.pciMin = filter.pciMin;
  if (filter.pciMax !== undefined) query.pciMax = filter.pciMax;
  if (filter.frequencyMhzMin !== undefined) query.frequencyMhzMin = filter.frequencyMhzMin;
  if (filter.frequencyMhzMax !== undefined) query.frequencyMhzMax = filter.frequencyMhzMax;
  if (filter.bandwidthMhzMin !== undefined) query.bandwidthMhzMin = filter.bandwidthMhzMin;
  if (filter.bandwidthMhzMax !== undefined) query.bandwidthMhzMax = filter.bandwidthMhzMax;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  return query;
}

export function parseSectorFilter(params: URLSearchParams): SectorFilter {
  const filter: SectorFilter = {};
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
  const baseStationId = params.get('baseStationId');
  if (baseStationId) filter.baseStationId = baseStationId;
  const sectorNumberMin = params.get('sectorNumberMin');
  if (sectorNumberMin !== null && sectorNumberMin !== '') filter.sectorNumberMin = Number(sectorNumberMin);
  const sectorNumberMax = params.get('sectorNumberMax');
  if (sectorNumberMax !== null && sectorNumberMax !== '') filter.sectorNumberMax = Number(sectorNumberMax);
  const pciMin = params.get('pciMin');
  if (pciMin !== null && pciMin !== '') filter.pciMin = Number(pciMin);
  const pciMax = params.get('pciMax');
  if (pciMax !== null && pciMax !== '') filter.pciMax = Number(pciMax);
  const frequencyMhzMin = params.get('frequencyMhzMin');
  if (frequencyMhzMin !== null && frequencyMhzMin !== '') filter.frequencyMhzMin = Number(frequencyMhzMin);
  const frequencyMhzMax = params.get('frequencyMhzMax');
  if (frequencyMhzMax !== null && frequencyMhzMax !== '') filter.frequencyMhzMax = Number(frequencyMhzMax);
  const bandwidthMhzMin = params.get('bandwidthMhzMin');
  if (bandwidthMhzMin !== null && bandwidthMhzMin !== '') filter.bandwidthMhzMin = Number(bandwidthMhzMin);
  const bandwidthMhzMax = params.get('bandwidthMhzMax');
  if (bandwidthMhzMax !== null && bandwidthMhzMax !== '') filter.bandwidthMhzMax = Number(bandwidthMhzMax);
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as SectorCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  const effectiveFromFrom = params.get('effectiveFromFrom');
  if (effectiveFromFrom) filter.effectiveFromFrom = effectiveFromFrom;
  const effectiveFromTo = params.get('effectiveFromTo');
  if (effectiveFromTo) filter.effectiveFromTo = effectiveFromTo;
  return filter;
}

export function serializeSectorFilter(filter: SectorFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.baseStationId) params.set('baseStationId', filter.baseStationId);
  if (filter.sectorNumberMin !== undefined) params.set('sectorNumberMin', String(filter.sectorNumberMin));
  if (filter.sectorNumberMax !== undefined) params.set('sectorNumberMax', String(filter.sectorNumberMax));
  if (filter.pciMin !== undefined) params.set('pciMin', String(filter.pciMin));
  if (filter.pciMax !== undefined) params.set('pciMax', String(filter.pciMax));
  if (filter.frequencyMhzMin !== undefined) params.set('frequencyMhzMin', String(filter.frequencyMhzMin));
  if (filter.frequencyMhzMax !== undefined) params.set('frequencyMhzMax', String(filter.frequencyMhzMax));
  if (filter.bandwidthMhzMin !== undefined) params.set('bandwidthMhzMin', String(filter.bandwidthMhzMin));
  if (filter.bandwidthMhzMax !== undefined) params.set('bandwidthMhzMax', String(filter.bandwidthMhzMax));
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  return params;
}

export function countSectorActiveFilters(filter: SectorFilter): number {
  return Array.from(serializeSectorFilter(filter).keys()).length;
}
