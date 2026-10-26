import type { LifecycleStatus } from '@/shared/types';
import type { CircuitTerminationFilter, CircuitTerminationSide } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCircuitTerminationQuery(filter: CircuitTerminationFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.circuitId) query.circuitId = filter.circuitId;
  if (filter.side?.length) query.side = filter.side.join(',');
  if (filter.portId) query.portId = filter.portId;
  if (filter.xconnectId) query.xconnectId = filter.xconnectId;
  if (filter.patchInfo) query.patchInfo = filter.patchInfo;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parseCircuitTerminationFilter(params: URLSearchParams): CircuitTerminationFilter {
  const filter: CircuitTerminationFilter = {};
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
  const side = params.get('side');
  if (side) filter.side = side.split(',') as CircuitTerminationSide[];
  const portId = params.get('portId');
  if (portId) filter.portId = portId;
  const xconnectId = params.get('xconnectId');
  if (xconnectId) filter.xconnectId = xconnectId;
  const patchInfo = params.get('patchInfo');
  if (patchInfo) filter.patchInfo = patchInfo;
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
  const labels = params.get('labels');
  if (labels) filter.labels = labels;
  return filter;
}

export function serializeCircuitTerminationFilter(filter: CircuitTerminationFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.circuitId) params.set('circuitId', filter.circuitId);
  if (filter.side?.length) params.set('side', filter.side.join(','));
  if (filter.portId) params.set('portId', filter.portId);
  if (filter.xconnectId) params.set('xconnectId', filter.xconnectId);
  if (filter.patchInfo) params.set('patchInfo', filter.patchInfo);
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countCircuitTerminationActiveFilters(filter: CircuitTerminationFilter): number {
  return Array.from(serializeCircuitTerminationFilter(filter).keys()).length;
}
