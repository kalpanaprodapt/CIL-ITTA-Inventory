import type { LifecycleStatus } from '@/shared/types';
import type { RackUnitFilter, RackUnitFace, RackUnitCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toRackUnitQuery(filter: RackUnitFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.rackId) query.rackId = filter.rackId;
  if (filter.unitNumberMin !== undefined) query.unitNumberMin = filter.unitNumberMin;
  if (filter.unitNumberMax !== undefined) query.unitNumberMax = filter.unitNumberMax;
  if (filter.occupied !== undefined) query.occupied = filter.occupied;
  if (filter.mountedDeviceId) query.mountedDeviceId = filter.mountedDeviceId;
  if (filter.face?.length) query.face = filter.face.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseRackUnitFilter(params: URLSearchParams): RackUnitFilter {
  const filter: RackUnitFilter = {};
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
  const rackId = params.get('rackId');
  if (rackId) filter.rackId = rackId;
  const unitNumberMin = params.get('unitNumberMin');
  if (unitNumberMin !== null && unitNumberMin !== '') filter.unitNumberMin = Number(unitNumberMin);
  const unitNumberMax = params.get('unitNumberMax');
  if (unitNumberMax !== null && unitNumberMax !== '') filter.unitNumberMax = Number(unitNumberMax);
  const occupied = params.get('occupied');
  if (occupied !== null) filter.occupied = occupied === 'true';
  const mountedDeviceId = params.get('mountedDeviceId');
  if (mountedDeviceId) filter.mountedDeviceId = mountedDeviceId;
  const face = params.get('face');
  if (face) filter.face = face.split(',') as RackUnitFace[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as RackUnitCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeRackUnitFilter(filter: RackUnitFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.rackId) params.set('rackId', filter.rackId);
  if (filter.unitNumberMin !== undefined) params.set('unitNumberMin', String(filter.unitNumberMin));
  if (filter.unitNumberMax !== undefined) params.set('unitNumberMax', String(filter.unitNumberMax));
  if (filter.occupied !== undefined) params.set('occupied', String(filter.occupied));
  if (filter.mountedDeviceId) params.set('mountedDeviceId', filter.mountedDeviceId);
  if (filter.face?.length) params.set('face', filter.face.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countRackUnitActiveFilters(filter: RackUnitFilter): number {
  return Array.from(serializeRackUnitFilter(filter).keys()).length;
}
