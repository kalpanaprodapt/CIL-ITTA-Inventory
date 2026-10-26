import type { LifecycleStatus } from '@/shared/types';
import type { AntennaFilter, AntennaCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toAntennaQuery(filter: AntennaFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.sectorId) query.sectorId = filter.sectorId;
  if (filter.model) query.model = filter.model;
  if (filter.heightMMin !== undefined) query.heightMMin = filter.heightMMin;
  if (filter.heightMMax !== undefined) query.heightMMax = filter.heightMMax;
  if (filter.azimuthDegMin !== undefined) query.azimuthDegMin = filter.azimuthDegMin;
  if (filter.azimuthDegMax !== undefined) query.azimuthDegMax = filter.azimuthDegMax;
  if (filter.tiltDegMin !== undefined) query.tiltDegMin = filter.tiltDegMin;
  if (filter.tiltDegMax !== undefined) query.tiltDegMax = filter.tiltDegMax;
  if (filter.gainDbiMin !== undefined) query.gainDbiMin = filter.gainDbiMin;
  if (filter.gainDbiMax !== undefined) query.gainDbiMax = filter.gainDbiMax;
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseAntennaFilter(params: URLSearchParams): AntennaFilter {
  const filter: AntennaFilter = {};
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
  const sectorId = params.get('sectorId');
  if (sectorId) filter.sectorId = sectorId;
  const model = params.get('model');
  if (model) filter.model = model;
  const heightMMin = params.get('heightMMin');
  if (heightMMin !== null && heightMMin !== '') filter.heightMMin = Number(heightMMin);
  const heightMMax = params.get('heightMMax');
  if (heightMMax !== null && heightMMax !== '') filter.heightMMax = Number(heightMMax);
  const azimuthDegMin = params.get('azimuthDegMin');
  if (azimuthDegMin !== null && azimuthDegMin !== '') filter.azimuthDegMin = Number(azimuthDegMin);
  const azimuthDegMax = params.get('azimuthDegMax');
  if (azimuthDegMax !== null && azimuthDegMax !== '') filter.azimuthDegMax = Number(azimuthDegMax);
  const tiltDegMin = params.get('tiltDegMin');
  if (tiltDegMin !== null && tiltDegMin !== '') filter.tiltDegMin = Number(tiltDegMin);
  const tiltDegMax = params.get('tiltDegMax');
  if (tiltDegMax !== null && tiltDegMax !== '') filter.tiltDegMax = Number(tiltDegMax);
  const gainDbiMin = params.get('gainDbiMin');
  if (gainDbiMin !== null && gainDbiMin !== '') filter.gainDbiMin = Number(gainDbiMin);
  const gainDbiMax = params.get('gainDbiMax');
  if (gainDbiMax !== null && gainDbiMax !== '') filter.gainDbiMax = Number(gainDbiMax);
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as AntennaCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeAntennaFilter(filter: AntennaFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.sectorId) params.set('sectorId', filter.sectorId);
  if (filter.model) params.set('model', filter.model);
  if (filter.heightMMin !== undefined) params.set('heightMMin', String(filter.heightMMin));
  if (filter.heightMMax !== undefined) params.set('heightMMax', String(filter.heightMMax));
  if (filter.azimuthDegMin !== undefined) params.set('azimuthDegMin', String(filter.azimuthDegMin));
  if (filter.azimuthDegMax !== undefined) params.set('azimuthDegMax', String(filter.azimuthDegMax));
  if (filter.tiltDegMin !== undefined) params.set('tiltDegMin', String(filter.tiltDegMin));
  if (filter.tiltDegMax !== undefined) params.set('tiltDegMax', String(filter.tiltDegMax));
  if (filter.gainDbiMin !== undefined) params.set('gainDbiMin', String(filter.gainDbiMin));
  if (filter.gainDbiMax !== undefined) params.set('gainDbiMax', String(filter.gainDbiMax));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countAntennaActiveFilters(filter: AntennaFilter): number {
  return Array.from(serializeAntennaFilter(filter).keys()).length;
}
