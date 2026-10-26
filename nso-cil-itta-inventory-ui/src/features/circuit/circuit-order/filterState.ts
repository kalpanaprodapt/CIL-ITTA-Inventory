import type { LifecycleStatus } from '@/shared/types';
import type { CircuitOrderFilter, CircuitOrderOrderStatus, CircuitOrderLifecycleStage } from './types';

type Query = Record<string, string | number | boolean | undefined>;

export function toCircuitOrderQuery(filter: CircuitOrderFilter): Query {
  const query: Query = {};
  if (filter.q) query.q = filter.q;
  if (filter.status?.length) query.status = filter.status.join(',');
  if (filter.code) query.code = filter.code;
  if (filter.name) query.name = filter.name;
  if (filter.externalRef) query.externalRef = filter.externalRef;
  if (filter.circuitId) query.circuitId = filter.circuitId;
  if (filter.orderNumber) query.orderNumber = filter.orderNumber;
  if (filter.orderedOnFrom) query.orderedOnFrom = filter.orderedOnFrom;
  if (filter.orderedOnTo) query.orderedOnTo = filter.orderedOnTo;
  if (filter.requestedDateFrom) query.requestedDateFrom = filter.requestedDateFrom;
  if (filter.requestedDateTo) query.requestedDateTo = filter.requestedDateTo;
  if (filter.committedDateFrom) query.committedDateFrom = filter.committedDateFrom;
  if (filter.committedDateTo) query.committedDateTo = filter.committedDateTo;
  if (filter.orderStatus?.length) query.orderStatus = filter.orderStatus.join(',');
  if (filter.effectiveFromFrom) query.effectiveFromFrom = filter.effectiveFromFrom;
  if (filter.effectiveFromTo) query.effectiveFromTo = filter.effectiveFromTo;
  if (filter.effectiveToFrom) query.effectiveToFrom = filter.effectiveToFrom;
  if (filter.effectiveToTo) query.effectiveToTo = filter.effectiveToTo;
  if (filter.labels) query.labels = filter.labels;
  if (filter.lifecycleStage?.length) query.lifecycleStage = filter.lifecycleStage.join(',');
  if (filter.sourceSystem) query.sourceSystem = filter.sourceSystem;
  return query;
}

export function parseCircuitOrderFilter(params: URLSearchParams): CircuitOrderFilter {
  const filter: CircuitOrderFilter = {};
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
  const orderNumber = params.get('orderNumber');
  if (orderNumber) filter.orderNumber = orderNumber;
  const orderedOnFrom = params.get('orderedOnFrom');
  if (orderedOnFrom) filter.orderedOnFrom = orderedOnFrom;
  const orderedOnTo = params.get('orderedOnTo');
  if (orderedOnTo) filter.orderedOnTo = orderedOnTo;
  const requestedDateFrom = params.get('requestedDateFrom');
  if (requestedDateFrom) filter.requestedDateFrom = requestedDateFrom;
  const requestedDateTo = params.get('requestedDateTo');
  if (requestedDateTo) filter.requestedDateTo = requestedDateTo;
  const committedDateFrom = params.get('committedDateFrom');
  if (committedDateFrom) filter.committedDateFrom = committedDateFrom;
  const committedDateTo = params.get('committedDateTo');
  if (committedDateTo) filter.committedDateTo = committedDateTo;
  const orderStatus = params.get('orderStatus');
  if (orderStatus) filter.orderStatus = orderStatus.split(',') as CircuitOrderOrderStatus[];
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
  if (lifecycleStage) filter.lifecycleStage = lifecycleStage.split(',') as CircuitOrderLifecycleStage[];
  const sourceSystem = params.get('sourceSystem');
  if (sourceSystem) filter.sourceSystem = sourceSystem;
  return filter;
}

export function serializeCircuitOrderFilter(filter: CircuitOrderFilter): URLSearchParams {
  const params = new URLSearchParams();
  if (filter.q) params.set('q', filter.q);
  if (filter.status?.length) params.set('status', filter.status.join(','));
  if (filter.code) params.set('code', filter.code);
  if (filter.name) params.set('name', filter.name);
  if (filter.externalRef) params.set('externalRef', filter.externalRef);
  if (filter.circuitId) params.set('circuitId', filter.circuitId);
  if (filter.orderNumber) params.set('orderNumber', filter.orderNumber);
  if (filter.orderedOnFrom) params.set('orderedOnFrom', filter.orderedOnFrom);
  if (filter.orderedOnTo) params.set('orderedOnTo', filter.orderedOnTo);
  if (filter.requestedDateFrom) params.set('requestedDateFrom', filter.requestedDateFrom);
  if (filter.requestedDateTo) params.set('requestedDateTo', filter.requestedDateTo);
  if (filter.committedDateFrom) params.set('committedDateFrom', filter.committedDateFrom);
  if (filter.committedDateTo) params.set('committedDateTo', filter.committedDateTo);
  if (filter.orderStatus?.length) params.set('orderStatus', filter.orderStatus.join(','));
  if (filter.effectiveFromFrom) params.set('effectiveFromFrom', filter.effectiveFromFrom);
  if (filter.effectiveFromTo) params.set('effectiveFromTo', filter.effectiveFromTo);
  if (filter.effectiveToFrom) params.set('effectiveToFrom', filter.effectiveToFrom);
  if (filter.effectiveToTo) params.set('effectiveToTo', filter.effectiveToTo);
  if (filter.labels) params.set('labels', filter.labels);
  if (filter.lifecycleStage?.length) params.set('lifecycleStage', filter.lifecycleStage.join(','));
  if (filter.sourceSystem) params.set('sourceSystem', filter.sourceSystem);
  return params;
}

export function countCircuitOrderActiveFilters(filter: CircuitOrderFilter): number {
  return Array.from(serializeCircuitOrderFilter(filter).keys()).length;
}
