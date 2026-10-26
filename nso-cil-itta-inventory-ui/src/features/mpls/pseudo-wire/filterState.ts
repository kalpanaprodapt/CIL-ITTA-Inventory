import type { LifecycleStatus } from '@/shared/types';
import type { PseudoWireFilter } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toPseudoWireQuery(filter: PseudoWireFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.vcIdMin !== undefined) query.vcIdMin = filter.vcIdMin;
  if (filter.vcIdMax !== undefined) query.vcIdMax = filter.vcIdMax;
  if (filter.aEndId) query.aEndId = filter.aEndId;
  if (filter.zEndId) query.zEndId = filter.zEndId;
  if (filter.encapsulation) query.encapsulation = filter.encapsulation;
  if (filter.controlWord !== undefined) query.controlWord = filter.controlWord;
  if (filter.costCenter) query.costCenter = filter.costCenter;
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  return query;
}

export function parsePseudoWireFilter(params: URLSearchParams): PseudoWireFilter {
  const filter: PseudoWireFilter = {};
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
  const vcIdMin = params.get('vcIdMin');
  if (vcIdMin !== null && vcIdMin !== '') filter.vcIdMin = Number(vcIdMin);
  const vcIdMax = params.get('vcIdMax');
  if (vcIdMax !== null && vcIdMax !== '') filter.vcIdMax = Number(vcIdMax);
  const aEndId = params.get('aEndId');
  if (aEndId) filter.aEndId = aEndId;
  const zEndId = params.get('zEndId');
  if (zEndId) filter.zEndId = zEndId;
  const encapsulation = params.get('encapsulation');
  if (encapsulation) filter.encapsulation = encapsulation;
  const controlWord = params.get('controlWord');
  if (controlWord !== null) filter.controlWord = controlWord === 'true';
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

export function serializePseudoWireFilter(filter: PseudoWireFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.vcIdMin !== undefined) params.set('vcIdMin', String(filter.vcIdMin));
  if (filter.vcIdMax !== undefined) params.set('vcIdMax', String(filter.vcIdMax));
  if (filter.aEndId) params.set('aEndId', filter.aEndId);
  if (filter.zEndId) params.set('zEndId', filter.zEndId);
  if (filter.encapsulation) params.set('encapsulation', filter.encapsulation);
  if (filter.controlWord !== undefined) params.set('controlWord', String(filter.controlWord));
  if (filter.costCenter) params.set('costCenter', filter.costCenter);
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  return params;
}

export function countPseudoWireActiveFilters(filter: PseudoWireFilter): number {
  return Array.from(serializePseudoWireFilter(filter).keys()).length;
}
