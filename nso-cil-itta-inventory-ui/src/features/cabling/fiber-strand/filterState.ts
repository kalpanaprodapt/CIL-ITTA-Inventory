import type { LifecycleStatus } from '@/shared/types';
import type { FiberStrandFilter, FiberStrandCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toFiberStrandQuery(filter: FiberStrandFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.cableId) query.cableId = filter.cableId;
  if (filter.strandNumberMin !== undefined) query.strandNumberMin = filter.strandNumberMin;
  if (filter.strandNumberMax !== undefined) query.strandNumberMax = filter.strandNumberMax;
  if (filter.bufferTube) query.bufferTube = filter.bufferTube;
  if (filter.tubeColor) query.tubeColor = filter.tubeColor;
  if (filter.attenuationDbMin !== undefined) query.attenuationDbMin = filter.attenuationDbMin;
  if (filter.attenuationDbMax !== undefined) query.attenuationDbMax = filter.attenuationDbMax;
  if (filter.inUse !== undefined) query.inUse = filter.inUse;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseFiberStrandFilter(params: URLSearchParams): FiberStrandFilter {
  const filter: FiberStrandFilter = {};
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
  const cableId = params.get('cableId');
  if (cableId) filter.cableId = cableId;
  const strandNumberMin = params.get('strandNumberMin');
  if (strandNumberMin !== null && strandNumberMin !== '') filter.strandNumberMin = Number(strandNumberMin);
  const strandNumberMax = params.get('strandNumberMax');
  if (strandNumberMax !== null && strandNumberMax !== '') filter.strandNumberMax = Number(strandNumberMax);
  const bufferTube = params.get('bufferTube');
  if (bufferTube) filter.bufferTube = bufferTube;
  const tubeColor = params.get('tubeColor');
  if (tubeColor) filter.tubeColor = tubeColor;
  const attenuationDbMin = params.get('attenuationDbMin');
  if (attenuationDbMin !== null && attenuationDbMin !== '') filter.attenuationDbMin = Number(attenuationDbMin);
  const attenuationDbMax = params.get('attenuationDbMax');
  if (attenuationDbMax !== null && attenuationDbMax !== '') filter.attenuationDbMax = Number(attenuationDbMax);
  const inUse = params.get('inUse');
  if (inUse !== null) filter.inUse = inUse === 'true';
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as FiberStrandCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeFiberStrandFilter(filter: FiberStrandFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.cableId) params.set('cableId', filter.cableId);
  if (filter.strandNumberMin !== undefined) params.set('strandNumberMin', String(filter.strandNumberMin));
  if (filter.strandNumberMax !== undefined) params.set('strandNumberMax', String(filter.strandNumberMax));
  if (filter.bufferTube) params.set('bufferTube', filter.bufferTube);
  if (filter.tubeColor) params.set('tubeColor', filter.tubeColor);
  if (filter.attenuationDbMin !== undefined) params.set('attenuationDbMin', String(filter.attenuationDbMin));
  if (filter.attenuationDbMax !== undefined) params.set('attenuationDbMax', String(filter.attenuationDbMax));
  if (filter.inUse !== undefined) params.set('inUse', String(filter.inUse));
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countFiberStrandActiveFilters(filter: FiberStrandFilter): number {
  return Array.from(serializeFiberStrandFilter(filter).keys()).length;
}
