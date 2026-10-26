import type { LifecycleStatus } from '@/shared/types';
import type { CircuitSegmentFilter, CircuitSegmentCriticality } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCircuitSegmentQuery(filter: CircuitSegmentFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.circuitId) query.circuitId = filter.circuitId;
  if (filter.segmentOrderMin !== undefined) query.segmentOrderMin = filter.segmentOrderMin;
  if (filter.segmentOrderMax !== undefined) query.segmentOrderMax = filter.segmentOrderMax;
  if (filter.aTerminationId) query.aTerminationId = filter.aTerminationId;
  if (filter.zTerminationId) query.zTerminationId = filter.zTerminationId;
  if (filter.latencyMsMin !== undefined) query.latencyMsMin = filter.latencyMsMin;
  if (filter.latencyMsMax !== undefined) query.latencyMsMax = filter.latencyMsMax;
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  if (filter.region) query.region = filter.region;
  if (filter.owner) query.owner = filter.owner;
  if (filter.criticality?.length) query.criticality = filter.criticality.join(',');
  if (filter.costCenter) query.costCenter = filter.costCenter;
  return query;
}

export function parseCircuitSegmentFilter(params: URLSearchParams): CircuitSegmentFilter {
  const filter: CircuitSegmentFilter = {};
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
  const circuitId = params.get('circuitId');
  if (circuitId) filter.circuitId = circuitId;
  const segmentOrderMin = params.get('segmentOrderMin');
  if (segmentOrderMin !== null && segmentOrderMin !== '') filter.segmentOrderMin = Number(segmentOrderMin);
  const segmentOrderMax = params.get('segmentOrderMax');
  if (segmentOrderMax !== null && segmentOrderMax !== '') filter.segmentOrderMax = Number(segmentOrderMax);
  const aTerminationId = params.get('aTerminationId');
  if (aTerminationId) filter.aTerminationId = aTerminationId;
  const zTerminationId = params.get('zTerminationId');
  if (zTerminationId) filter.zTerminationId = zTerminationId;
  const latencyMsMin = params.get('latencyMsMin');
  if (latencyMsMin !== null && latencyMsMin !== '') filter.latencyMsMin = Number(latencyMsMin);
  const latencyMsMax = params.get('latencyMsMax');
  if (latencyMsMax !== null && latencyMsMax !== '') filter.latencyMsMax = Number(latencyMsMax);
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  const region = params.get('region');
  if (region) filter.region = region;
  const owner = params.get('owner');
  if (owner) filter.owner = owner;
  const criticality = params.get('criticality');
  if (criticality) filter.criticality = criticality.split(',') as CircuitSegmentCriticality[];
  const costCenter = params.get('costCenter');
  if (costCenter) filter.costCenter = costCenter;
  return filter;
}

export function serializeCircuitSegmentFilter(filter: CircuitSegmentFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.circuitId) params.set('circuitId', filter.circuitId);
  if (filter.segmentOrderMin !== undefined) params.set('segmentOrderMin', String(filter.segmentOrderMin));
  if (filter.segmentOrderMax !== undefined) params.set('segmentOrderMax', String(filter.segmentOrderMax));
  if (filter.aTerminationId) params.set('aTerminationId', filter.aTerminationId);
  if (filter.zTerminationId) params.set('zTerminationId', filter.zTerminationId);
  if (filter.latencyMsMin !== undefined) params.set('latencyMsMin', String(filter.latencyMsMin));
  if (filter.latencyMsMax !== undefined) params.set('latencyMsMax', String(filter.latencyMsMax));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  if (filter.region) params.set('region', filter.region);
  if (filter.owner) params.set('owner', filter.owner);
  if (filter.criticality?.length) params.set('criticality', filter.criticality.join(','));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  return params;
}

export function countCircuitSegmentActiveFilters(filter: CircuitSegmentFilter): number {
  return Array.from(serializeCircuitSegmentFilter(filter).keys()).length;
}
